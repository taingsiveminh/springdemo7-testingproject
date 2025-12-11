package com.example.demo.service;

import com.example.demo.entity.Stock;
import com.example.demo.model.BaseResponseModel;
import com.example.demo.model.BaseResponseWithDataModel;
import com.example.demo.model.stock.StockModel;
import com.example.demo.model.stock.UpdateStockModel;
import com.example.demo.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StockService {
    @Autowired
    private StockRepository stockRepository;

    public ResponseEntity<BaseResponseWithDataModel> listStock() {
        List<Stock> stocks = stockRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseWithDataModel("success","successfully retrieved stock",stocks));
    }


    public ResponseEntity<BaseResponseModel> createStock(StockModel stock) {
        Stock stockEntity = new Stock();
        stockEntity.setQuantity(stock.getQuantity());
        stockEntity.setProductId(stock.getProductId());
        stockEntity.setCreatedAt(LocalDateTime.now());
        stockRepository.save(stockEntity);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new BaseResponseModel("success","successfully created stock"));
    }
    public ResponseEntity<BaseResponseModel> adjustQuantity(Long stockId, UpdateStockModel updateStock)
    {
        Optional <Stock> existingStock = stockRepository.findById(stockId);
        //stock not found in db
        if (existingStock.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponseModel("fail","stock not found with id " +stockId));
        }
        Stock stock = existingStock.get();

        if (updateStock.getOperationType()==1) {
            int newQty = stock.getQuantity() + updateStock.getQuantity();
            stock.setQuantity(newQty);
        } else if (updateStock.getOperationType()==2) {
            //when remove amount > existing amount
            if (stock.getQuantity()< updateStock.getQuantity()){
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                        .body(new BaseResponseModel("fail","quantity to remove can not be exceeded " +
                                "than existing stock"+ stock.getQuantity()));
            }
            int newQty = stock.getQuantity() - updateStock.getQuantity();
            stock.setQuantity(newQty);
            
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new BaseResponseModel("fail","invalid operation type"));
        }
        stock.setUpdatedAt(LocalDateTime.now());
        stockRepository.save(stock);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseModel("success","successfully adjusted stock quantity "));

    }

    public ResponseEntity<BaseResponseModel> deleteStock(Long stockId) {
        if (!stockRepository.existsById(stockId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponseModel("fail","stock not found with id :" + stockId));

        }
        stockRepository.deleteById(stockId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseModel("success","successfully deleted stock"));
    }
}
