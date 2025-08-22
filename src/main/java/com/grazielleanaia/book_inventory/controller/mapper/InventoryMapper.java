package com.grazielleanaia.book_inventory.controller.mapper;


import com.grazielleanaia.book_inventory.controller.dto.InventoryDTORequest;
import com.grazielleanaia.book_inventory.controller.dto.InventoryDTOResponse;
import com.grazielleanaia.book_inventory.infrastructure.entity.InventoryEntity;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface InventoryMapper {
    InventoryDTOResponse toDTOResponse(InventoryEntity entity);

    InventoryEntity toEntity(InventoryDTORequest request);

    List<InventoryDTOResponse> toListInventoryDTOResponse(List<InventoryEntity> entityList);

}
