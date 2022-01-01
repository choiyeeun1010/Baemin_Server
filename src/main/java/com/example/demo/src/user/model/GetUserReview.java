package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetUserReview {
    private int reviewIdx;
    private String storeName;
    private String image;
    private String review;
    private String menuName;
    private float scope;
    private String good;
}
