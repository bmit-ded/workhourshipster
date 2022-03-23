package com.contexity.whh.service.mapper;

import com.contexity.whh.domain.Tags;
import com.contexity.whh.service.dto.TagsDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tags} and its DTO {@link TagsDTO}.
 */
@Mapper(componentModel = "spring", uses = { EntryMapper.class })
public interface TagsMapper extends EntityMapper<TagsDTO, Tags> {
    @Mapping(target = "entries", source = "entries", qualifiedByName = "idSet")
    TagsDTO toDto(Tags s);

    @Mapping(target = "removeEntry", ignore = true)
    Tags toEntity(TagsDTO tagsDTO);
}
