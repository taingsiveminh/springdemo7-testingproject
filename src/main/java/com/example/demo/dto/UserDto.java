package com.example.demo.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private long id;
    private String name;
    private int age;
    private String address;
    private String email;
    private String password;
    private String role ="USER";

}
