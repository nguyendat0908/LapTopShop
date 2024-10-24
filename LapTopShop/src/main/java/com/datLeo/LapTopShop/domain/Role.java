package com.datLeo.LapTopShop.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.*;

@Entity
@Table(name = "roles")
@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    long id;
    String name;
    String description;

    // ROLE one -> to many USER
    @OneToMany(mappedBy = "role")
    List<User> users;

}
