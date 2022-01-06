package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetUserMain {
    private int userIdx;
    private String addressName;
    private List<String> categoryNames;
    private List<String> images;
    //private int categoryIdx;
    //private String categoryName;
    //private String categoryImage;
}
