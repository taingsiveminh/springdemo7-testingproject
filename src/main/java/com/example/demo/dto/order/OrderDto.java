package com.example.demo.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
public class OrderDto {
    @JsonProperty("items")
    @NotNull(message = "order items is required")
    @NotEmpty(message = "order items cannot be empty")
    private List<OrderItemDto> orderItems;
}
