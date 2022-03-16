package com.contexity.whh.service.mapper;

import com.contexity.whh.domain.Project;
import com.contexity.whh.service.dto.ProjectDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Project} and its DTO {@link ProjectDTO}.
 */
@Mapper(componentModel = "spring", uses = { CustomerMapper.class })
public interface ProjectMapper extends EntityMapper<ProjectDTO, Project> {
    @Mapping(target = "customer", source = "customer", qualifiedByName = "name")
    ProjectDTO toDto(Project s);

    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ProjectDTO toDtoName(Project project);
}
