package com.contexity.whh.service.mapper;

import com.contexity.whh.domain.Worksheet;
import com.contexity.whh.service.dto.WorksheetDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Worksheet} and its DTO {@link WorksheetDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface WorksheetMapper extends EntityMapper<WorksheetDTO, Worksheet> {
    @Mapping(target = "user", source = "user", qualifiedByName = "login")
    WorksheetDTO toDto(Worksheet s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    WorksheetDTO toDtoId(Worksheet worksheet);
}
