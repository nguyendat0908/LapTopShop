package com.DatLeo.LapTopShop.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.*;

@Entity
@Table(name = "users")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String email;
    String password;
    String fullName;
    String address;
    String phone;
    String avatar;

    // USER one -> to many ORDER
    @OneToMany(mappedBy = "user")
    List<Order> orders;

    // USER one -> to one CART
    @OneToOne(mappedBy = "user")
    Cart cart;

    // USER many -> to one ROLE
    @ManyToOne
    @JoinColumn(name = "role_id")
    Role role;
    
}
