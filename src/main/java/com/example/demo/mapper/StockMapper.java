package com.example.demo.mapper;

import com.example.demo.dto.stock.StockDto;
import com.example.demo.dto.stock.StockResponseDto;
import com.example.demo.entity.Stock;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StockMapper {
    public Stock toEntity(StockDto dto){
        Stock entity = new Stock();

        entity.setQuantity(dto.getQuantity());
        entity.setProductId(dto.getProductId());


        return entity;
    }
    public StockResponseDto toDto(Stock entity){
        StockResponseDto dto = new StockResponseDto();

        dto.setId(entity.getId());
        dto.setProductId(entity.getProductId());
        dto.setQty(entity.getQuantity());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        return dto;
    }
    public List<StockResponseDto> toDoList(List<Stock> entities) {
        if (entities == null || entities.isEmpty()){
            return new ArrayList<>();
        }
        return entities.stream()
                .map(stock -> this.toDto(stock))
                .collect(Collectors.toList());
    }
}
