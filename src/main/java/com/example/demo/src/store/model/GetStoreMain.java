package com.example.demo.src.store.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetStoreMain {
    private int storeIdx;
    private String storeName;
    private String storeCallNum;
    private String image;
    private String higienInformation;
    private String minAmount;
    private String methodName;
    private String deliveryTime;
    private String deliveryPrice;
    private float starGrade;
    private String reviewNum;
    private String countComments;
    private String likeNum;
}
