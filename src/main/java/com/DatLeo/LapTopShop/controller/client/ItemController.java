package com.DatLeo.LapTopShop.controller.client;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.DatLeo.LapTopShop.domain.Product;
import com.DatLeo.LapTopShop.service.ProductService;


@Controller
public class ItemController {

    private final ProductService productService;

    public ItemController(ProductService productService){
        this.productService = productService;
    }
    
    @GetMapping("/product/{id}")
    public String getMethodName(Model model, @PathVariable long id) {

        Product product = this.productService.getProductById(id);
        Pageable pageable = PageRequest.of(0, 8);
        Page<Product> prs = this.productService.getAllProductPage(pageable);
        List<Product> products = prs.getContent();
        model.addAttribute("products", products);
        model.addAttribute("product", product);
        model.addAttribute("id", id);

        return "client/product/productDetail";
    }
    
}
