package com.example.demo.service;

import com.example.demo.dto.order.OrderDto;
import com.example.demo.dto.order.OrderItemDto;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderItem;
import com.example.demo.entity.Stock;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.model.BaseResponseModel;
import com.example.demo.model.BaseResponseWithDataModel;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.StockRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderMapper mapper;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private StockRepository
            stockRepository;
    public ResponseEntity<BaseResponseWithDataModel> listOrders() {
        List<Order> orders = orderRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseWithDataModel("success", "successfully retrieved orders", mapper.toResponseDtoList(orders)));
    }
    @Transactional
    public ResponseEntity<BaseResponseModel> createOrder(OrderDto payload) {
        // maps for product ids
        List<Long> productIds = payload.getOrderItems().stream()
                .map(OrderItemDto::getProductId)
                .toList();
        // get stocks product ids
        List<Stock> stocks = stockRepository.findByProductIdIn(productIds, Sort.by(Sort.Direction.ASC,
                "createdAt"));

        // map for required quantity of productsIds
        // example : 1: 100. 2: 50
        Map<Long,Integer> requiredQuantities = payload.getOrderItems().stream()
                .collect(Collectors.toMap(OrderItemDto::getProductId, OrderItemDto::getAmount));

        // deduct stocks for each product
        // [1,3]
        for (Long ProductId: requiredQuantities.keySet()){
            // quantity to deduct
            int remain = requiredQuantities.get(ProductId);

            // filter stocks by product id
            List<Stock> stocksByProduct = stocks.stream()
                    .filter(stock -> stock.getProduct().getId().equals(ProductId))
                    .toList();
            // calculate and compare quantity
            for (Stock stock: stocksByProduct){
                if (remain <= 0) break;
                int available = stock.getQuantity();

                if (available >= remain) {
                    stock.setQuantity(available - remain);
                    remain = 0;
                }else {
                    stock.setQuantity(0);
                    remain -= available;
                }
            }

            if (remain > 0) {
                throw new RuntimeException("Not enough stock for product id:" + ProductId);
            }

        }
        // save updated stocks to db
        stockRepository.saveAll(stocks);


        //crate order entity
        Order order = mapper.toEntity(payload);

        orderRepository.save(order);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new BaseResponseModel("success","successfully placed order"));
    }
}
