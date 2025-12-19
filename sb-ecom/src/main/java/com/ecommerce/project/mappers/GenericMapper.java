package com.ecommerce.project.mappers;

import org.mapstruct.MappingTarget;

public interface GenericMapper<E, Req, Res> {

    Res toResponse(E entity);

    E toEntity (Req dto);

    void updateEntityFromDto(Req dto, @MappingTarget E entity);

}
