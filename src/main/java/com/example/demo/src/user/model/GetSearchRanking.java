package com.example.demo.src.user.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.naming.Name;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class GetSearchRanking {
    private String searchContents;
    private Timestamp createat;
}
