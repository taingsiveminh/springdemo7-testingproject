package com.example.demo.mapper;

import com.example.demo.dto.order.OrderItemDto;

import com.example.demo.entity.OrderItem;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper {
    public OrderItem toEntity(OrderItemDto dto) {
        OrderItem entity = new OrderItem();
        entity.setProductId(dto.getProductId());
        entity.setQuantity(dto.getAmount());
        return entity;
    }
}
