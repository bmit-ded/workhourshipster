package com.contexity.whh.service;

import com.contexity.whh.service.dto.ProjectDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.contexity.whh.domain.Project}.
 */
public interface ProjectService {
    /**
     * Save a project.
     *
     * @param projectDTO the entity to save.
     * @return the persisted entity.
     */
    ProjectDTO save(ProjectDTO projectDTO);

    /**
     * Partially updates a project.
     *
     * @param projectDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProjectDTO> partialUpdate(ProjectDTO projectDTO);

    /**
     * Get all the projects.
     *
     * @return the list of entities.
     */
    List<ProjectDTO> findAll();

    /**
     * Get the "id" project.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProjectDTO> findOne(Long id);

    /**
     * Delete the "id" project.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
