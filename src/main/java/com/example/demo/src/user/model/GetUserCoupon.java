package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.naming.Name;

@Getter
@Setter
@AllArgsConstructor
public class GetUserCoupon {
    private int couponIdx;
    private String couponName;
    private String couponImage;
    private String couponPrice;
    private String minAmount;
    private String term;
}
