package com.DatLeo.LapTopShop.service;

import java.io.File;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.DatLeo.LapTopShop.domain.Product;
import com.DatLeo.LapTopShop.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

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
    public Product getProductById(long id){
        return this.productRepository.findById(id);
    }

    // Delete product by id
    public void deleteProductById(long id){
        this.productRepository.deleteById(id);
    }

}
