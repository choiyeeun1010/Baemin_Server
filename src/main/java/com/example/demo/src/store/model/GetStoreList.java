package com.example.demo.src.store.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetStoreList {
    private int storeIdx;
    private String image;
    private String storeName;
    private String deliveryTime;
    private String minAmount;
    private String deliveryPrice;
    private float starGrade;
    private int reviewCount;
    private String mainMenu;
}
