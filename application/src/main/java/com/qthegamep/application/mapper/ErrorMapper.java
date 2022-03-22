package com.qthegamep.application.mapper;

import com.qthegamep.application.dto.ErrorResponseDTO;
import com.qthegamep.application.entity.Error;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ErrorMapper {

    ErrorMapper INSTANCE = Mappers.getMapper(ErrorMapper.class);

    @Mappings({
            @Mapping(source = "errorCode", target = "errorCode")
    })
    ErrorResponseDTO errorToErrorResponseDTO(Error error);
}
