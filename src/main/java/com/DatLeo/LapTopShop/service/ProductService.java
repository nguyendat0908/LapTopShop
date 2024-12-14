package com.DatLeo.LapTopShop.service;

import java.io.File;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.DatLeo.LapTopShop.domain.Cart;
import com.DatLeo.LapTopShop.domain.CartDetail;
import com.DatLeo.LapTopShop.domain.Order;
import com.DatLeo.LapTopShop.domain.OrderDetail;
import com.DatLeo.LapTopShop.domain.Product;
import com.DatLeo.LapTopShop.domain.User;
import com.DatLeo.LapTopShop.repository.CartDetailRepository;
import com.DatLeo.LapTopShop.repository.CartRepository;
import com.DatLeo.LapTopShop.repository.OrderDetailRepository;
import com.DatLeo.LapTopShop.repository.OrderRepository;
import com.DatLeo.LapTopShop.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final UserService userService;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    // Json to Data
    public void ImportDataToJson(String filePath) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        // Read Json reverts to Object
        List<Product> products = objectMapper.readValue(
                new File(filePath), objectMapper.getTypeFactory()
                        .constructCollectionType(List.class, Product.class));

        this.productRepository.saveAll(products);
    }

    // Get all product with pagination
    public Page<Product> getAllProductPage(Pageable pageable) {
        return this.productRepository.findAll(pageable);
    }

    // Save product
    public Product handleSaveProduct(Product product) {
        return this.productRepository.save(product);
    }

    // Get product by id
    public Optional<Product> getProductById(long id) {
        return this.productRepository.findById(id);
    }

    // Delete product by id
    public void deleteProductById(long id) {
        this.productRepository.deleteById(id);
    }

    // Add product to cart
    public void handleAddProductToCart(String email, long productID, HttpSession session, long quantity) {

        // Check user đã có cart hay chưa -> Chưa có thì tạo mới
        User user = this.userService.getUserByEmail(email);

        if (user != null) {
            Cart cart = this.cartRepository.findByUser(user);

            if (cart == null) {
                // Tạo mới cart
                Cart otherCart = new Cart();
                otherCart.setUser(user);
                otherCart.setSum(0);

                this.cartRepository.save(otherCart);
            }

            // Lưu cartdetail
            // Find product by id
            Optional<Product> p = this.productRepository.findById(productID);
            if (p.isPresent()) {
                Product realProduct = p.get();

                // Check sản phẩm đã từng được thêm vào giỏ hàng trước đây chưa?
                CartDetail oldDetail = this.cartDetailRepository.findByCartAndProduct(cart, realProduct);

                if (oldDetail == null) {

                    CartDetail cartDetail = new CartDetail();
                    cartDetail.setCart(cart);
                    cartDetail.setProduct(realProduct);
                    cartDetail.setPrice(realProduct.getPrice());
                    cartDetail.setQuantity(quantity);

                    this.cartDetailRepository.save(cartDetail);

                    // Update cart (sum)
                    int s = cart.getSum() + 1;
                    cart.setSum(s);
                    this.cartRepository.save(cart);
                    session.setAttribute("sum", s);
                } else {
                    oldDetail.setQuantity(oldDetail.getQuantity() + quantity);
                    this.cartDetailRepository.save(oldDetail);
                }

            }

        }
    }

    public Cart fetchByUser(User user) {
        return this.cartRepository.findByUser(user);
    }

    public void handleRemoveCartDetail(long cartDetailID, HttpSession session) {

        // Tìm cartDetail dựa trên ID
        Optional<CartDetail> cartDetailOptional = this.cartDetailRepository.findById(cartDetailID);

        if (cartDetailOptional.isPresent()) {
            CartDetail cartDetail = cartDetailOptional.get();

            Cart currentCart = cartDetail.getCart();

            // Xóa
            this.cartDetailRepository.deleteById(cartDetailID);

            if (currentCart.getSum() > 1) {
                int s = currentCart.getSum() - 1;
                currentCart.setSum(s);
                session.setAttribute("sum", s);
            } else {
                this.cartRepository.deleteById(currentCart.getId());
                session.setAttribute("sum", 0);
            }
        }
    }

    // Update cart
    public void handleUpdateCartBeforeCheckout(List<CartDetail> cartDetails) {

        for (CartDetail cartDetail : cartDetails) {
            Optional<CartDetail> cdOptional = this.cartDetailRepository.findById(cartDetail.getId());
            if (cdOptional.isPresent()) {
                CartDetail currenCartDetail = cdOptional.get();
                currenCartDetail.setQuantity(cartDetail.getQuantity());
                this.cartDetailRepository.save(currenCartDetail);
            }
        }
    }

    public void handleProductOrder(User user, HttpSession session, String receiverName, String receiverAddress,
            String receiverPhone) {

        // Create orderDetail
        // Step 1: Get cart by user
        Cart cart = this.cartRepository.findByUser(user);
        if (cart != null) {
            List<CartDetail> cartDetails = cart.getCartDetails();

            // Create order
            Order order = new Order();
            order.setUser(user);
            order.setReceiverName(receiverName);
            order.setReceiverAddress(receiverAddress);
            order.setReceiverPhone(receiverPhone);
            order.setStatus("PENDING");

            double sum = 0;
            for (CartDetail cartDetail : cartDetails) {
                sum += cartDetail.getPrice();
            }
            order.setTotalPrice(sum);

            order = this.orderRepository.save(order);

            if (cartDetails != null) {
                for (CartDetail cd : cartDetails) {
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrder(order);
                    orderDetail.setProduct(cd.getProduct());
                    orderDetail.setPrice(cd.getPrice());
                    orderDetail.setQuantity(cd.getQuantity());

                    this.orderDetailRepository.save(orderDetail);
                }

                // Step 2: Delete cartDetail and cart
                for (CartDetail cd : cartDetails) {
                    this.cartDetailRepository.deleteById(cd.getId());
                }

                this.cartRepository.deleteById(cart.getId());

                // Step 3: Update session
                session.setAttribute("sum", 0);
            }
        }
    }

}
