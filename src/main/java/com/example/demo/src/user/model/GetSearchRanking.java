package com.example.demo.src.user.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.naming.Name;

@Getter
@Setter
@AllArgsConstructor
public class GetSearchRanking {
    private String searchContents;
    private String createAt;
}
