package com.example.demo.src.order.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetOrder {
    private int orderIdx;
    private String storeName;
    private String menuName;
    private String menuCount;
    private String price;
}
