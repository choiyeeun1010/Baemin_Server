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
    private int minamount;
    private String methodName;
    private String deliveryTime;
    private int deliveryPrice;
    private float scope;
    private int reviewIdx;
    private int comentIdx;
    private int userIdx;
    private String likeState;
    private int methodIdx;
}
