package com.grazielleanaia.book_inventory.controller.mapper;


import com.grazielleanaia.book_inventory.controller.dto.InventoryDTORequest;
import com.grazielleanaia.book_inventory.infrastructure.entity.InventoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;


@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)

public interface InventoryUpdateMapper {

    InventoryEntity updateInventory(InventoryDTORequest dtoRequest, @MappingTarget InventoryEntity entity);
}
