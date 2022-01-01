package com.example.demo.src.order.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetOrderReceipt {
    private int orderIdx;
    private int storeIdx;
    private String storeName;
    private String menuName;
    private String menuPrice;
    private int menuCount;
    private String createAt;
    private String storeCallNum;
    private String sideName;
    private String sidePrice;
    private String deliveryPrice;
    private String orderPrice;
    private String totalPrice;
    private String methodName;
    private String address;
    private String userPhone;
    private String orderRequest;
    private String riderRequest;
}
