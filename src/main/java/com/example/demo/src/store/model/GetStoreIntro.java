package com.example.demo.src.store.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetStoreIntro {
    private int storeIdx;
    private String storeName;
    private String image;
    private String storeIntro;
}
