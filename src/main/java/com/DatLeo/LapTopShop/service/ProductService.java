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

    public void ImportDataToJson(String filePath) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        // Read Json reverts to Object
        List<Product> products = objectMapper.readValue(
                new File(filePath), objectMapper.getTypeFactory()
                        .constructCollectionType(List.class, Product.class));

        this.productRepository.saveAll(products);
    }

    public Page<Product> getAllProductPage(Pageable pageable){
        return this.productRepository.findAll(pageable);
    }

    public Product handleSaveProduct(Product product){
        return this.productRepository.save(product);
    }

    public Product getProductById(long id){
        return this.productRepository.findById(id);
    }

    public void deleteProductById(long id){
        this.productRepository.deleteById(id);
    }

}
