package com.DatLeo.LapTopShop.service;

import java.io.File;
import java.util.List;

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

}
