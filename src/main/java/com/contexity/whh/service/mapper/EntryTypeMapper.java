package com.contexity.whh.service.mapper;

import com.contexity.whh.domain.EntryType;
import com.contexity.whh.service.dto.EntryTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EntryType} and its DTO {@link EntryTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EntryTypeMapper extends EntityMapper<EntryTypeDTO, EntryType> {
    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    EntryTypeDTO toDtoName(EntryType entryType);
}
