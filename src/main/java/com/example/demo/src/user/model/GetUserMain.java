package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetUserMain {
    private int userIdx;
    private String addressName;
    private int categoryIdx;
    private String categoryName;
    private String categoryImage;
}
