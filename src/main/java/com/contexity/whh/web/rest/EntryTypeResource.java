package com.contexity.whh.web.rest;

import com.contexity.whh.repository.EntryTypeRepository;
import com.contexity.whh.service.EntryTypeService;
import com.contexity.whh.service.dto.EntryTypeDTO;
import com.contexity.whh.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.contexity.whh.domain.EntryType}.
 */
@RestController
@RequestMapping("/api")
public class EntryTypeResource {

    private final Logger log = LoggerFactory.getLogger(EntryTypeResource.class);

    private static final String ENTITY_NAME = "entryType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EntryTypeService entryTypeService;

    private final EntryTypeRepository entryTypeRepository;

    public EntryTypeResource(EntryTypeService entryTypeService, EntryTypeRepository entryTypeRepository) {
        this.entryTypeService = entryTypeService;
        this.entryTypeRepository = entryTypeRepository;
    }

    /**
     * {@code POST  /entry-types} : Create a new entryType.
     *
     * @param entryTypeDTO the entryTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new entryTypeDTO, or with status {@code 400 (Bad Request)} if the entryType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/entry-types")
    public ResponseEntity<EntryTypeDTO> createEntryType(@Valid @RequestBody EntryTypeDTO entryTypeDTO) throws URISyntaxException {
        log.debug("REST request to save EntryType : {}", entryTypeDTO);
        if (entryTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new entryType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EntryTypeDTO result = entryTypeService.save(entryTypeDTO);
        return ResponseEntity
            .created(new URI("/api/entry-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /entry-types/:id} : Updates an existing entryType.
     *
     * @param id the id of the entryTypeDTO to save.
     * @param entryTypeDTO the entryTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entryTypeDTO,
     * or with status {@code 400 (Bad Request)} if the entryTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the entryTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/entry-types/{id}")
    public ResponseEntity<EntryTypeDTO> updateEntryType(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EntryTypeDTO entryTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EntryType : {}, {}", id, entryTypeDTO);
        if (entryTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, entryTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!entryTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EntryTypeDTO result = entryTypeService.save(entryTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, entryTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /entry-types/:id} : Partial updates given fields of an existing entryType, field will ignore if it is null
     *
     * @param id the id of the entryTypeDTO to save.
     * @param entryTypeDTO the entryTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entryTypeDTO,
     * or with status {@code 400 (Bad Request)} if the entryTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the entryTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the entryTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/entry-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EntryTypeDTO> partialUpdateEntryType(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EntryTypeDTO entryTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EntryType partially : {}, {}", id, entryTypeDTO);
        if (entryTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, entryTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!entryTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EntryTypeDTO> result = entryTypeService.partialUpdate(entryTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, entryTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /entry-types} : get all the entryTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entryTypes in body.
     */
    @GetMapping("/entry-types")
    public List<EntryTypeDTO> getAllEntryTypes() {
        log.debug("REST request to get all EntryTypes");
        return entryTypeService.findAll();
    }

    /**
     * {@code GET  /entry-types/:id} : get the "id" entryType.
     *
     * @param id the id of the entryTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the entryTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/entry-types/{id}")
    public ResponseEntity<EntryTypeDTO> getEntryType(@PathVariable Long id) {
        log.debug("REST request to get EntryType : {}", id);
        Optional<EntryTypeDTO> entryTypeDTO = entryTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(entryTypeDTO);
    }

    /**
     * {@code DELETE  /entry-types/:id} : delete the "id" entryType.
     *
     * @param id the id of the entryTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/entry-types/{id}")
    public ResponseEntity<Void> deleteEntryType(@PathVariable Long id) {
        log.debug("REST request to delete EntryType : {}", id);
        entryTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
