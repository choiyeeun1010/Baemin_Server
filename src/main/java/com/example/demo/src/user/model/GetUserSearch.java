package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetUserSearch {
    private int userIdx;
    private int searchIdx;
    private String searchContents;
}
