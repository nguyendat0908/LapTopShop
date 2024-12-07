package com.DatLeo.LapTopShop.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.*;

@Entity
@Table(name = "products")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @NotNull
    @NotEmpty(message = "Tên sản phẩm không được để trống")
    String name;

    @NotNull
    @DecimalMin(value = "0", inclusive = false, message = "Gía phải lớn hơn 0")
    double price;
    String image;

    @Column(columnDefinition = "TEXT")
    @NotNull
    @NotEmpty(message = "Mô tả chi tiết không được để trống")
    String detailDesc;
    
    @NotNull
    @NotEmpty(message = "Mô tả ngắn không được để trống")
    String shortDesc;

    @NotNull
    @Min(value = 1, message = "Số lượng cần lớn hơn hoặc bằng 1")
    long quantity;
    long sold;
    String factory;
    String target;

    // PRODUCT one -> to many CART_DETAIL
    @OneToMany(mappedBy = "product")
    List<CartDetail> cartDetails;

    // PRODUCT one -> to many ORDER_DETAIL
    @OneToMany(mappedBy = "product")
    List<OrderDetail> orderDetails;
    
}
