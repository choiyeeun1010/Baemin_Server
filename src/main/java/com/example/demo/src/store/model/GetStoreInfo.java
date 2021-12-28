package com.example.demo.src.store.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetStoreInfo {
    private int storeIdx;
    private String storeName;
    private String openTime;
    private String closeTime;
    private String storeHoliday;
    private String storeCallNum;
    private String noticeContents;
    private int orderPrice;
    private int deliveryPrice;
    private String regionName;
    private int orderIdx;
    private int reviewIdx;
    private int likeStoreIdx;
}
