package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostUserReq {
    private String userID;
    private String userName;
    private String userNickName;
    private String email;
    private String password;
    private String userPhone;
    private String mailAgree;
    private String smsAgree;

//    private String UserName;
//    private String id;
//    private String email;
//    private String password;
}
