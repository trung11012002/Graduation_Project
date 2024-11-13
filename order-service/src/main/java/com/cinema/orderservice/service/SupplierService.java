package com.cinema.orderservice.service;

import com.cinema.orderservice.dto.request.SupplierCreate;
import com.cinema.orderservice.dto.response.SupplierResponse;

public interface SupplierService {
    SupplierResponse getSupplierById(Integer id);
    SupplierResponse createSupplier(SupplierCreate supplierRequest);
}
