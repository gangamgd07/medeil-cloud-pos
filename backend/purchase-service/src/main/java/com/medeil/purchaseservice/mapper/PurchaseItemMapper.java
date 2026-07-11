package com.medeil.purchaseservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.medeil.purchaseservice.dto.PurchaseItemDTO;
import com.medeil.purchaseservice.entity.PurchaseItem;

@Mapper(componentModel = "spring")
public interface PurchaseItemMapper {
	
	    PurchaseItemDTO toDTO(PurchaseItem entity);

	    PurchaseItem toEntity(PurchaseItemDTO dto);

	    @Mapping(target = "purchaseItemId", ignore = true)
	    void updateEntityFromDTO(PurchaseItemDTO dto,
	                             @MappingTarget PurchaseItem entity);

}
