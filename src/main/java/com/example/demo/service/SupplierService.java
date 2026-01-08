package com.example.demo.service;

import com.example.demo.dto.supplier.SupplierDto;
import com.example.demo.dto.supplier.UpdateSupplierDto;
import com.example.demo.entity.Supplier;
import com.example.demo.mapper.SupplierMapper;
import com.example.demo.model.BaseResponseModel;
import com.example.demo.model.BaseResponseWithDataModel;
import com.example.demo.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {
    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private SupplierMapper mapper;

    public ResponseEntity<BaseResponseWithDataModel> listSupplier(){
        List<Supplier> suppliers = supplierRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseWithDataModel(
                        "success",
                        "successfully retrieved suppliers",mapper.toDtoList(suppliers)));
    }

    public ResponseEntity<BaseResponseModel> createSupplier(SupplierDto dto ){
        if (supplierRepository.existsByName(dto.getName())){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new BaseResponseModel("fail","supplier already existed with name:"+
                            dto.getName()));
        }
        Supplier supplier = mapper.toEntity(dto);

        supplierRepository.save(supplier);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseModel("success","successfully created supplier"));
    }

    public ResponseEntity<BaseResponseModel> updateSupplier(Long supplierId, UpdateSupplierDto dto){
        Optional<Supplier> existingSupplier = supplierRepository.findById(supplierId);
        // if supplier not found return 404
        if (existingSupplier.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponseModel("fail","supplier not found with id:"+supplierId));

        }
        Supplier supplier = existingSupplier.get();
        mapper.updateEntityFromDto(supplier,dto);

        supplierRepository.save(supplier);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseModel("success","successfully updated supplier"));
    }
    public ResponseEntity<BaseResponseModel> deleteSupplier(Long supplierId) {
        if (!supplierRepository.existsById(supplierId)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponseModel("fail","supplier not found with id:"+supplierId));

        }
        supplierRepository.deleteById(supplierId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseModel("success","successfully deleted supplier"));
    }
}
