package com.cinema.orderservice.controller;

import com.cinema.orderservice.dto.request.SupplierCreate;
import com.cinema.orderservice.dto.response.ApiResponse;
import com.cinema.orderservice.dto.response.SupplierResponse;
import com.cinema.orderservice.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/supplier")
public class SupplierController {
    @Autowired
    private SupplierService supplierService;
    @GetMapping("/get-supplier/{id}")
    public ApiResponse<SupplierResponse> getSupplierById(@PathVariable Integer id) {
        return ApiResponse.<SupplierResponse>builder()
                .code(1000)
                .msg("success")
                .data(supplierService.getSupplierById(id))
                .build();
    }
    @PostMapping("/create-supplier")
    public ApiResponse<SupplierResponse> createSupplier(@RequestBody SupplierCreate supplierRequest) {
        return ApiResponse.<SupplierResponse>builder()
                .code(1000)
                .msg("success")
                .data(supplierService.createSupplier(supplierRequest))
                .build();
    }

}
