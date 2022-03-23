package com.contexity.whh.service.mapper;

import com.contexity.whh.domain.Worklocation;
import com.contexity.whh.service.dto.WorklocationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Worklocation} and its DTO {@link WorklocationDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WorklocationMapper extends EntityMapper<WorklocationDTO, Worklocation> {
    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    WorklocationDTO toDtoName(Worklocation worklocation);
}
