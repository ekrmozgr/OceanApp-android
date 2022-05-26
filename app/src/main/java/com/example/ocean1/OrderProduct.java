package com.example.ocean1;

import java.util.ArrayList;
import java.util.List;

public class OrderProduct {
    public OrderProduct()
    {
        product = new Product();coupons = new ArrayList<>();
    }
    Product product;
    int productQuantity;
    double productPrice;
    int productId;
    List<Coupon> coupons;
}
