package com.contexity.whh.service.mapper;

import com.contexity.whh.domain.Entry;
import com.contexity.whh.service.dto.EntryDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Entry} and its DTO {@link EntryDTO}.
 */
@Mapper(componentModel = "spring", uses = { WorksheetMapper.class, ProjectMapper.class, EntryTypeMapper.class, WorklocationMapper.class })
public interface EntryMapper extends EntityMapper<EntryDTO, Entry> {
    @Mapping(target = "worksheet", source = "worksheet", qualifiedByName = "id")
    @Mapping(target = "project", source = "project", qualifiedByName = "name")
    @Mapping(target = "entryType", source = "entryType", qualifiedByName = "name")
    @Mapping(target = "worklocation", source = "worklocation", qualifiedByName = "name")
    EntryDTO toDto(Entry s);

    @Named("idSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    Set<EntryDTO> toDtoIdSet(Set<Entry> entry);
}
