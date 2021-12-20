package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.naming.Name;

@Getter
@Setter
@AllArgsConstructor
public class GetUserAddress {
    private int userAddressIdx;
    private int userIdx;
    private String addressName;
    private String address;
}
