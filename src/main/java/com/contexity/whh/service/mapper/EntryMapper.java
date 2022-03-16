package com.contexity.whh.service.mapper;

import com.contexity.whh.domain.Entry;
import com.contexity.whh.service.dto.EntryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Entry} and its DTO {@link EntryDTO}.
 */
@Mapper(componentModel = "spring", uses = { WorksheetMapper.class, ProjectMapper.class, EntryTypeMapper.class })
public interface EntryMapper extends EntityMapper<EntryDTO, Entry> {
    @Mapping(target = "worksheet", source = "worksheet", qualifiedByName = "id")
    @Mapping(target = "project", source = "project", qualifiedByName = "name")
    @Mapping(target = "entryType", source = "entryType", qualifiedByName = "name")
    EntryDTO toDto(Entry s);
}
