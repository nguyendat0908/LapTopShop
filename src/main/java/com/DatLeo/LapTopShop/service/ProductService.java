package com.DatLeo.LapTopShop.service;

import java.io.File;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.DatLeo.LapTopShop.domain.Cart;
import com.DatLeo.LapTopShop.domain.CartDetail;
import com.DatLeo.LapTopShop.domain.Product;
import com.DatLeo.LapTopShop.domain.User;
import com.DatLeo.LapTopShop.repository.CartDetailRepository;
import com.DatLeo.LapTopShop.repository.CartRepository;
import com.DatLeo.LapTopShop.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final UserService userService;

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
    public Page<Product> getAllProductPage(Pageable pageable){
        return this.productRepository.findAll(pageable);
    }

    // Save product
    public Product handleSaveProduct(Product product){
        return this.productRepository.save(product);
    }

    // Get product by id
    public Optional<Product> getProductById(long id){
        return this.productRepository.findById(id);
    }

    // Delete product by id
    public void deleteProductById(long id){
        this.productRepository.deleteById(id);
    }

    // Add product to cart
    public void handleAddProductToCart(String email, long productID){

        // Check user đã có cart hay chưa -> Chưa có thì tạo mới
        User user = this.userService.getUserByEmail(email);

        if (user != null) {
            Cart cart = this.cartRepository.findByUser(user);

            if (cart == null) {
                // Tạo mới cart
                Cart otherCart = new Cart();
                otherCart.setUser(user);
                otherCart.setSum(1);

                this.cartRepository.save(otherCart);
            }

            // Lưu cartdetail
            // Find product by id
            Optional<Product> p = this.productRepository.findById(productID);
            if (p.isPresent()) {
                Product realProduct = p.get();

                CartDetail cartDetail = new CartDetail();
                cartDetail.setCart(cart);
                cartDetail.setProduct(realProduct);
                cartDetail.setPrice(realProduct.getPrice());
                cartDetail.setQuantity(1);

                this.cartDetailRepository.save(cartDetail);
            }
            
        }
    }

}
