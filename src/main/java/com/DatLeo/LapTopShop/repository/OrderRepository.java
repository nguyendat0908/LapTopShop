package com.DatLeo.LapTopShop.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DatLeo.LapTopShop.domain.Order;
import com.DatLeo.LapTopShop.domain.User;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    Page<Order> findAll(Pageable pageable);

    List<Order> findByUser(User user);
}
