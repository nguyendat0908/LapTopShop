package com.DatLeo.LapTopShop.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.*;;

@Entity
@Table(name = "orders")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    double totalPrice;

    String receiverName;

    String receiverAddress;

    String receiverPhone;

    String status;

    String paymentRef;
    
    String paymentStatus;

    String paymentMethod;

    // ORDER one -> to many ORDER_DETAIL
    @OneToMany(mappedBy = "order")
    List<OrderDetail> orderDetails;

    // ORDER many -> to one USER
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
