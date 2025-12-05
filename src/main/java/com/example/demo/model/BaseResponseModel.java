package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class BaseResponseModel {
    private String status;
    private String message;

}
