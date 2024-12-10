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
import org.springframework.web.bind.annotation.PathVariable;

import com.DatLeo.LapTopShop.domain.Cart;
import com.DatLeo.LapTopShop.domain.CartDetail;
import com.DatLeo.LapTopShop.domain.Product;
import com.DatLeo.LapTopShop.domain.User;
import com.DatLeo.LapTopShop.service.ProductService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;




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
    public String getMethodName(Model model) {

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
        this.productService.handleAddProductToCart(email, productId, session);

        return "redirect:/";
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
    
}
