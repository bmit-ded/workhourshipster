package com.contexity.whh.service;

import com.contexity.whh.service.dto.WorklocationDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.contexity.whh.domain.Worklocation}.
 */
public interface WorklocationService {
    /**
     * Save a worklocation.
     *
     * @param worklocationDTO the entity to save.
     * @return the persisted entity.
     */
    WorklocationDTO save(WorklocationDTO worklocationDTO);

    /**
     * Partially updates a worklocation.
     *
     * @param worklocationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<WorklocationDTO> partialUpdate(WorklocationDTO worklocationDTO);

    /**
     * Get all the worklocations.
     *
     * @return the list of entities.
     */
    List<WorklocationDTO> findAll();

    /**
     * Get the "id" worklocation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WorklocationDTO> findOne(Long id);

    /**
     * Delete the "id" worklocation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
