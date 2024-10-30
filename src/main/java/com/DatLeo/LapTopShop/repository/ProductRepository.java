package com.DatLeo.LapTopShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DatLeo.LapTopShop.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
}
