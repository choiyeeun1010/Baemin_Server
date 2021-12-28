package com.example.demo.src.store.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetStoreMenu {
    private int menuIdx;
    private String menuPrice;
    private String menuImage;
    private String menuIntro;
    private int menuCategoryIdx;
}
