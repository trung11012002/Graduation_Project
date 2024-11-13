package com.cinema.orderservice.mapper;

import com.cinema.orderservice.dto.request.ItemFoodDto;
import com.cinema.orderservice.entity.ItemFood;
import com.cinema.orderservice.entity.Supplier;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface ItemFoodMapper {

    @Mapping(target = "supplierName", source = "supplier")
    ItemFoodDto toItemFoodDto(ItemFood itemFood);

    default String mapSupplierToSupplierName(Supplier supplier){
        return supplier.getName();
    }
}
