package com.DatLeo.LapTopShop.controller.client;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.DatLeo.LapTopShop.domain.Product;
import com.DatLeo.LapTopShop.service.ProductService;


@Controller
public class HomePageController {

    private final ProductService productService;

    public HomePageController(ProductService productService){
        this.productService = productService;
    }
    
    @GetMapping("/")
    public String getHomePage(Model model) {

        Pageable pageable = PageRequest.of(0, 8);
        Page<Product> prs = this.productService.getAllProductPage(pageable);
        List<Product> products = prs.getContent();
        model.addAttribute("products", products);

        return "client/homepage/show";
    }
    
}
