package com.DatLeo.LapTopShop.service;

import org.springframework.stereotype.Service;

import com.DatLeo.LapTopShop.repository.ProductRepository;
import com.DatLeo.LapTopShop.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DashboardService {
    
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public long countUsers(){
        return this.userRepository.count();
    }

    public long countProducts(){
        return this.productRepository.count();
    }
}
