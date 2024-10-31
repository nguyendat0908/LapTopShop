package com.DatLeo.LapTopShop.controller.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.DatLeo.LapTopShop.domain.Product;
import com.DatLeo.LapTopShop.service.ProductService;
import com.DatLeo.LapTopShop.service.UploadFileService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;



@Controller
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final UploadFileService uploadFileService;

    // Import data
    @GetMapping("/importData")
    @ResponseBody
    public String getImportData() {
        try {
            productService.ImportDataToJson("src/main/resources/json/product.json");
            return "Import success!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error importing data: " + e.getMessage();
        }
    }

    // Display All Product
    @GetMapping("/admin/product")
    public String getAllProductPage(Model model, @RequestParam("page") Optional<String> optionalPage) {

        int page = 1;
        try {
            if (optionalPage.isPresent()) {
                page = Integer.parseInt(optionalPage.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Pageable pageable = PageRequest.of(page - 1, 10);
        Page<Product> pagePr = this.productService.getAllProductPage(pageable);
        List<Product> listProducts = pagePr.getContent();

        model.addAttribute("products", listProducts);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pagePr.getTotalPages());

        return "admin/product/show";
    }
    

    // Create Product Page
    @GetMapping("/admin/product/create")
    public String getCreateProductPage(Model model) {

        model.addAttribute("newProduct", new Product());

        return "admin/product/create";
    }

    // Create Product
    @PostMapping("/admin/product/create")
    public String PostCreateProduct(Model model, @ModelAttribute("newProduct") Product product, @RequestParam("uploadFile") MultipartFile file) {

        String image = this.uploadFileService.handleSaveUploadFile(file, "product");
    
        product.setImage(image);

        this.productService.handleSaveProduct(product);

        return "redirect:/admin/product";
    }

    // View Detail Product
    @GetMapping("/admin/product/{id}")
    public String getViewDetailProduct(Model model, @PathVariable long id) {

        Product product = this.productService.getProductById(id);

        model.addAttribute("product", product);

        return "admin/product/detailProduct";
    }
    

}
