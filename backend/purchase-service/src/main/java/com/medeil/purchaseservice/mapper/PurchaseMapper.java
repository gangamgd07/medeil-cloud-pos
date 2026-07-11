package com.medeil.purchaseservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.medeil.purchaseservice.dto.PurchaseDTO;
import com.medeil.purchaseservice.entity.Purchase;

@Mapper(
	    componentModel = "spring",
	    uses = PurchaseItemMapper.class
	)
public interface PurchaseMapper {
	
	PurchaseDTO toDTO(Purchase entity);

    Purchase toEntity(PurchaseDTO dto);

    @Mapping(target = "purchaseId", ignore = true)
    @Mapping(target = "purchaseItems", ignore = true)
    void updateEntityFromDTO(PurchaseDTO dto,
                             @MappingTarget Purchase entity);

}
