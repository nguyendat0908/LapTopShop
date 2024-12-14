package com.DatLeo.LapTopShop.controller.client;

import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.DatLeo.LapTopShop.domain.Cart;
import com.DatLeo.LapTopShop.domain.CartDetail;
import com.DatLeo.LapTopShop.domain.Product;
import com.DatLeo.LapTopShop.domain.User;
import com.DatLeo.LapTopShop.service.ProductService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class ItemController {

    private final ProductService productService;

    public ItemController(ProductService productService){
        this.productService = productService;
    }
    
    @GetMapping("/product/{id}")
    public String getMethodName(Model model, @PathVariable long id) {

        Product product = this.productService.getProductById(id).get();
        Pageable pageable = PageRequest.of(0, 8);
        Page<Product> prs = this.productService.getAllProductPage(pageable);
        List<Product> products = prs.getContent();
        model.addAttribute("products", products);
        model.addAttribute("product", product);
        model.addAttribute("id", id);

        return "client/product/productDetail";
    }

    @GetMapping("/contact")
    public String getContactPage(Model model) {

        // Tọa độ vị trí
        model.addAttribute("latitude", "20.3437133");
        model.addAttribute("longitude", "106.4767506");
        model.addAttribute("apiKey", "AIzaSyBOmMMUtdp4twr2nLY9DFmNKSZU7qxLF2s");

        return "client/contact/shopContact";
    }

    @PostMapping("/add-product-to-cart/{id}")
    public String addProductToCart(@PathVariable long id, HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        
        long productId = id;
        String email = (String) session.getAttribute("email");
        this.productService.handleAddProductToCart(email, productId, session, 1);

        // Quay trở lại trang gốc
        String referer = request.getHeader("Referer");
        if (referer == null || referer.isEmpty()) {
            referer = "/";
        }
        return "redirect:" + referer;
    }

    @GetMapping("/cart")
    public String getCartPage(Model model, HttpServletRequest request) {

        User currentUser = new User(); 
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        currentUser.setId(id);

        Cart cart = this.productService.fetchByUser(currentUser);

        List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();

        double totalPrice = 0;
        for (CartDetail cartDetail : cartDetails) {
            totalPrice += cartDetail.getPrice() * cartDetail.getQuantity();
        }

        model.addAttribute("cartDetails", cartDetails);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("cart", cart);

        return "client/cart/show";
    }

    @PostMapping("/delete-cart-product/{id}")
    public String deleteCartProduct(Model model, @PathVariable long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        long cartDetailID = id;
        this.productService.handleRemoveCartDetail(cartDetailID, session);
        
        return "redirect:/cart";
    }
    
    @PostMapping("/confirm-checkout")
    public String postCheckOutPage(@ModelAttribute("cart") Cart cart) {
        
        List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();
        this.productService.handleUpdateCartBeforeCheckout(cartDetails);

        return "redirect:/checkout";
    }

    @GetMapping("/checkout")
    public String getCheckOutPage(Model model, HttpServletRequest request) {
        User currentUser = new User();// null
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        currentUser.setId(id);

        Cart cart = this.productService.fetchByUser(currentUser);

        List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();

        double totalPrice = 0;
        for (CartDetail cd : cartDetails) {
            totalPrice += cd.getPrice() * cd.getQuantity();
        }

        model.addAttribute("cartDetails", cartDetails);
        model.addAttribute("totalPrice", totalPrice);

        return "client/cart/checkout";
    }

    @PostMapping("/place-order")
    public String handlePlaceOrder(HttpServletRequest request, 
    @RequestParam("receiverName") String receiverName,
    @RequestParam("receiverAddress") String receiverAddress,
    @RequestParam("receiverPhone") String receiverPhone) {

        User currentUser = new User();// null
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        currentUser.setId(id);

        this.productService.handleProductOrder(currentUser, session, receiverName, receiverAddress, receiverPhone);

        return "redirect:/thanks";
    }

    @GetMapping("/thanks")
    public String getThankYouPage(Model model) {
        return "client/cart/thanks";
    }
    
    @PostMapping("/add-product-from-view-detail")
    public String handleAddProductFromViewDetail(@RequestParam("id") long id,
            @RequestParam("quantity") long quantity, HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        String email = (String) session.getAttribute("email");
        this.productService.handleAddProductToCart(email, id, session, quantity);

        return "redirect:/product/" + id;
    }

}
