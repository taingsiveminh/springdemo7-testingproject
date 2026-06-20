package com.example.demo.controller;

import com.example.demo.dto.order.OrderDto;
import com.example.demo.model.BaseResponseModel;
import com.example.demo.model.BaseResponseWithDataModel;
import com.example.demo.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<BaseResponseWithDataModel> listOrder() {
        return orderService.listOrders();
    }

    @PostMapping
    public ResponseEntity<BaseResponseModel> placeOrder(@Valid @RequestBody OrderDto payload) {
    return orderService.createOrder(payload);
    }
}
