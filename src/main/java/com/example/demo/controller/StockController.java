package com.example.demo.controller;

import com.example.demo.dto.stock.StockDto;
import com.example.demo.model.BaseResponseModel;
import com.example.demo.model.BaseResponseWithDataModel;
import com.example.demo.dto.stock.UpdateStockDto;
import com.example.demo.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/stocks")
public class StockController {
    @Autowired
    private StockService stockService;

    @GetMapping
    public ResponseEntity<BaseResponseWithDataModel> listStock(){
        return stockService.listStock();
    }

    @GetMapping("{id}")
    public ResponseEntity<BaseResponseWithDataModel> getStock(@PathVariable("id") Long stockId){
        return stockService.getStock(stockId);
    }

    @PostMapping
    public ResponseEntity<BaseResponseModel> createdStock(@RequestBody StockDto payload) {
        return stockService.createStock(payload);
    }
    @PatchMapping("{id}")
    public ResponseEntity<BaseResponseModel> adjustQuantity(@PathVariable("id") long stockId,@RequestBody
    UpdateStockDto payload){
        return stockService.adjustQuantity(stockId,payload);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<BaseResponseModel> deleteStock(@PathVariable("id") long stockId) {
       return stockService.deleteStock(stockId);
    }
}
