package com.example.demo.model.stock;

import lombok.Data;

@Data
public class UpdateStockModel {
    private Integer operationType;
    private Integer quantity;
}
