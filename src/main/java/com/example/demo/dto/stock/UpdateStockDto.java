package com.example.demo.dto.stock;

import lombok.Data;

@Data
public class UpdateStockDto {
    private Integer operationType;
    private Integer quantity;
}
