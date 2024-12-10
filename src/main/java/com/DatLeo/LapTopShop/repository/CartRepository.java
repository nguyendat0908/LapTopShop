package com.DatLeo.LapTopShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DatLeo.LapTopShop.domain.Cart;
import com.DatLeo.LapTopShop.domain.User;



@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    
    Cart findByUser(User user);
}
