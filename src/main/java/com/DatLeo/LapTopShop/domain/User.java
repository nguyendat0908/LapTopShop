package com.DatLeo.LapTopShop.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.*;


@Entity
@Table(name = "users")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @NotNull
    @Email(message = "Email không đúng định dạng", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    String email;

    @NotNull
    @Size(min = 2, message = "Password phải có tối thiểu 2 ký tự")
    // @StrongPassword
    String password;

    @NotNull
    @Size(min = 2, message = "Tên phải có tối thiểu 2 ký tự")
    String fullName;

    String address;
    String phone;
    String avatar;

    String provider;

    @PrePersist
    public void prePersist(){
        if (this.provider == null) {
            this.provider = "LOCAL";
        }
    }

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
