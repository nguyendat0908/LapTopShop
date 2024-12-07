package com.DatLeo.LapTopShop.controller.client;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.DatLeo.LapTopShop.domain.Product;
import com.DatLeo.LapTopShop.domain.User;
import com.DatLeo.LapTopShop.domain.dto.RegisterDTO;
import com.DatLeo.LapTopShop.service.ProductService;
import com.DatLeo.LapTopShop.service.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class HomePageController {

    private final ProductService productService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public HomePageController(ProductService productService, UserService userService, PasswordEncoder passwordEncoder){
        this.productService = productService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
    
    @GetMapping("/")
    public String getHomePage(Model model) {

        Pageable pageable = PageRequest.of(0, 8);
        Page<Product> prs = this.productService.getAllProductPage(pageable);
        List<Product> products = prs.getContent();
        model.addAttribute("products", products);

        return "client/homepage/show";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {

        model.addAttribute("registerUser", new RegisterDTO());
        return "client/auth/register";
    }

    @PostMapping("/register")
    public String handleRegister(@ModelAttribute("registerUser") @Valid RegisterDTO registerDTO, BindingResult bindingResult) {

        // Validate
        if (bindingResult.hasErrors()) {
            return "client/auth/register";
        }
        
        User user = this.userService.registerDTOtoUser(registerDTO);

        String hashPassword = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);
        user.setRole(this.userService.getRoleByName("USER"));

        // save
        this.userService.handleSaveUser(user);

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {


        return "client/auth/login";
    }
    
    

    @GetMapping("/products")
    public String getMethodName(Model model, @RequestParam("page") Optional<String> optionalPage) {

        int page = 1;

        try {
            if (optionalPage.isPresent()) {
                page = Integer.parseInt(optionalPage.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Pageable pageable = PageRequest.of(page - 1, 9);
        Page<Product> prs = this.productService.getAllProductPage(pageable);
        List<Product> products = prs.getContent();

        model.addAttribute("products", products);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", prs.getTotalPages());
        return "client/product/show";
    }
    
    
}
