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
    private int mainAmount;
    private int deliveryPrice;
    private float scope;
    private String menuName;
    private String menuState;
    private int menuCategoryIdx;
}
