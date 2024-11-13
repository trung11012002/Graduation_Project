package com.cinema.orderservice.service.Impl;

import com.cinema.orderservice.dto.request.SupplierCreate;
import com.cinema.orderservice.dto.response.SupplierResponse;
import com.cinema.orderservice.entity.Supplier;
import com.cinema.orderservice.exception.AppException;
import com.cinema.orderservice.exception.ErrorCode;
import com.cinema.orderservice.mapper.SupplierMapper;
import com.cinema.orderservice.repository.SupplierRepo;
import com.cinema.orderservice.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SupplierServiceImpl implements SupplierService {
    @Autowired
    private SupplierRepo supplierRepository;
    @Autowired
    private SupplierMapper supplierMapper;
    @Override
    public SupplierResponse getSupplierById(Integer id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SUPPLIER_NOT_FOUND));
        return supplierMapper.supplierToSupplierResponse(supplier);
    }

    @Override
    public SupplierResponse createSupplier(SupplierCreate supplierRequest) {
        Supplier supplier = supplierMapper.supplierCreateToSupplier(supplierRequest);
        supplierRepository.save(supplier);
        return supplierMapper.supplierToSupplierResponse(supplier);
    }
}
