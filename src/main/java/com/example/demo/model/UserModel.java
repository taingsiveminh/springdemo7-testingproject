package com.example.demo.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    private long id;
    private String name;
    private int age;
    private String address;
    private String email;
    private String role ="USER";
}
