package com.contexity.whh.service;

import com.contexity.whh.service.dto.EntryTypeDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.contexity.whh.domain.EntryType}.
 */
public interface EntryTypeService {
    /**
     * Save a entryType.
     *
     * @param entryTypeDTO the entity to save.
     * @return the persisted entity.
     */
    EntryTypeDTO save(EntryTypeDTO entryTypeDTO);

    /**
     * Partially updates a entryType.
     *
     * @param entryTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EntryTypeDTO> partialUpdate(EntryTypeDTO entryTypeDTO);

    /**
     * Get all the entryTypes.
     *
     * @return the list of entities.
     */
    List<EntryTypeDTO> findAll();

    /**
     * Get the "id" entryType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EntryTypeDTO> findOne(Long id);

    /**
     * Delete the "id" entryType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
