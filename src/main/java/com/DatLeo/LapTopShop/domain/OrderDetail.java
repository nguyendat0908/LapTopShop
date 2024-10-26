package com.DatLeo.LapTopShop.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "order_details")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetail {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    long quantity;
    double price;

    // ORDER_DETAIL many -> to one ORDER
    @ManyToOne
    @JoinColumn(name = "order_id")
    Order order;

    // ORDER_DETAIL many -> to one PRODUCT
    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;

}
