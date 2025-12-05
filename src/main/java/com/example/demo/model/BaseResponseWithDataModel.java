package com.example.demo.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BaseResponseWithDataModel extends BaseResponseModel {
    private Object data;

    public BaseResponseWithDataModel(String status,String message, Object data) {
        super(status,message);
        this.data = data;
    }

}
