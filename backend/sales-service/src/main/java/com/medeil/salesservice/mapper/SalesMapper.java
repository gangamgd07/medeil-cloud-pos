package com.medeil.salesservice.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.medeil.salesservice.dto.SalesDTO;
import com.medeil.salesservice.entity.Sale;

@Mapper(componentModel = "spring")
public interface SalesMapper {

    Sale toEntity(SalesDTO dto);

    SalesDTO toDTO(Sale entity);

    List<SalesDTO> toDTOList(List<Sale> entities);
}
