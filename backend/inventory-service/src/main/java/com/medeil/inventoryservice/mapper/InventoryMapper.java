package com.medeil.inventoryservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.medeil.inventoryservice.dto.InventoryDTO;
import com.medeil.inventoryservice.entity.Inventory;

@Mapper(componentModel = "spring")
public interface InventoryMapper {

    InventoryDTO toDTO(Inventory inventory);

    Inventory toEntity(InventoryDTO dto);

    @Mapping(target = "inventoryId", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    void updateEntityFromDTO(InventoryDTO dto,
                             @MappingTarget Inventory inventory);
}