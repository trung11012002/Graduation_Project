package com.cinema.orderservice.repository;

import com.cinema.orderservice.dto.response.SupplierResponse;
import com.cinema.orderservice.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepo extends JpaRepository<Supplier,Integer> {
}
