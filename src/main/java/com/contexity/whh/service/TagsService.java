package com.contexity.whh.service;

import com.contexity.whh.service.dto.TagsDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.contexity.whh.domain.Tags}.
 */
public interface TagsService {
    /**
     * Save a tags.
     *
     * @param tagsDTO the entity to save.
     * @return the persisted entity.
     */
    TagsDTO save(TagsDTO tagsDTO);

    /**
     * Partially updates a tags.
     *
     * @param tagsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TagsDTO> partialUpdate(TagsDTO tagsDTO);

    /**
     * Get all the tags.
     *
     * @return the list of entities.
     */
    List<TagsDTO> findAll();

    /**
     * Get all the tags with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TagsDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" tags.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TagsDTO> findOne(Long id);

    /**
     * Delete the "id" tags.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
