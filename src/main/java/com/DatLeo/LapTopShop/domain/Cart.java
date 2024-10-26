package com.datLeo.LapTopShop.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.*;

@Entity
@Table(name = "carts")
@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    long id;
    long sum;
    
    // CART one -> to one USER
    @OneToOne
    @JoinColumn(name = "user_id")
    User user;

    // CART one -> to many CART_DETAILS
    @OneToMany(mappedBy = "cart")
    List<CartDetail> cartDetails;
}
