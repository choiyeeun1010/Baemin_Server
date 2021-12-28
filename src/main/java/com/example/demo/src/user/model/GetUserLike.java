package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.naming.Name;

@Getter
@Setter
@AllArgsConstructor
public class GetUserLike {
    private int storeIdx;
    private String likeState;
    private String storeName;
    private String image;
    private String minAmount;
    private String deliveryPrice;
    private float starGrade;
    private int reviewCount;
    private String mainMenu;
}
