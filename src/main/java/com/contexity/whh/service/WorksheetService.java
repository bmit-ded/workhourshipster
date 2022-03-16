package com.contexity.whh.service;

import com.contexity.whh.service.dto.WorksheetDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.contexity.whh.domain.Worksheet}.
 */
public interface WorksheetService {
    /**
     * Save a worksheet.
     *
     * @param worksheetDTO the entity to save.
     * @return the persisted entity.
     */
    WorksheetDTO save(WorksheetDTO worksheetDTO);

    /**
     * Partially updates a worksheet.
     *
     * @param worksheetDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<WorksheetDTO> partialUpdate(WorksheetDTO worksheetDTO);

    /**
     * Get all the worksheets.
     *
     * @return the list of entities.
     */
    List<WorksheetDTO> findAll();

    /**
     * Get all the worksheets with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WorksheetDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" worksheet.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WorksheetDTO> findOne(Long id);

    /**
     * Delete the "id" worksheet.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
