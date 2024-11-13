package com.cinema.orderservice.mapper;
import com.cinema.orderservice.dto.request.SupplierCreate;
import com.cinema.orderservice.dto.response.ItemFoodResponse;
import com.cinema.orderservice.dto.response.SupplierResponse;
import com.cinema.orderservice.entity.ItemFood;
import com.cinema.orderservice.entity.Supplier;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SupplierMapper {
    @Mapping(source = "itemFoods", target = "itemFoodResponses")
    SupplierResponse supplierToSupplierResponse(Supplier supplier);

    ItemFoodResponse itemFoodToItemFoodResponse(ItemFood itemFood);

    List<ItemFoodResponse> itemFoodsToItemFoodResponses(List<ItemFood> itemFoods);

    Supplier supplierCreateToSupplier(SupplierCreate supplierCreate);
}
