package com.example.demo.mapper;

import com.example.demo.dto.order.OrderDto;
import com.example.demo.dto.order.OrderItemDto;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {
    @Autowired
    private OrderItemMapper orderItemMapper;

    public Order toEntity(OrderDto dto){
        Order entity = new Order();
        List<OrderItem> orderItemsEntities = dto.getOrderItems()
                .stream()
                .map((OrderItemDto  orderItemDto) -> {
                    OrderItem orderItem = orderItemMapper.toEntity(orderItemDto);
                    orderItem.setOrder(entity);
                    return orderItem;
                })
                .toList();
        entity.setItems(orderItemsEntities);
        return entity;

    }


}
