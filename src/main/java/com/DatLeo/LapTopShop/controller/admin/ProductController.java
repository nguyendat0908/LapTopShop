package com.DatLeo.LapTopShop.controller.admin;

import org.springframework.stereotype.Controller;

import com.DatLeo.LapTopShop.service.ProductService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/importData")
    public String getImportData() {
        try {
            productService.ImportDataToJson("product.json");
            return "Import success!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error importing data: " + e.getMessage();
        }
    }

}
