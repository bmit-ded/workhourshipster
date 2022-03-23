package com.contexity.whh.web.rest;

import com.contexity.whh.repository.WorklocationRepository;
import com.contexity.whh.service.WorklocationService;
import com.contexity.whh.service.dto.WorklocationDTO;
import com.contexity.whh.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.contexity.whh.domain.Worklocation}.
 */
@RestController
@RequestMapping("/api")
public class WorklocationResource {

    private final Logger log = LoggerFactory.getLogger(WorklocationResource.class);

    private static final String ENTITY_NAME = "worklocation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WorklocationService worklocationService;

    private final WorklocationRepository worklocationRepository;

    public WorklocationResource(WorklocationService worklocationService, WorklocationRepository worklocationRepository) {
        this.worklocationService = worklocationService;
        this.worklocationRepository = worklocationRepository;
    }

    /**
     * {@code POST  /worklocations} : Create a new worklocation.
     *
     * @param worklocationDTO the worklocationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new worklocationDTO, or with status {@code 400 (Bad Request)} if the worklocation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/worklocations")
    public ResponseEntity<WorklocationDTO> createWorklocation(@RequestBody WorklocationDTO worklocationDTO) throws URISyntaxException {
        log.debug("REST request to save Worklocation : {}", worklocationDTO);
        if (worklocationDTO.getId() != null) {
            throw new BadRequestAlertException("A new worklocation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorklocationDTO result = worklocationService.save(worklocationDTO);
        return ResponseEntity
            .created(new URI("/api/worklocations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /worklocations/:id} : Updates an existing worklocation.
     *
     * @param id the id of the worklocationDTO to save.
     * @param worklocationDTO the worklocationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated worklocationDTO,
     * or with status {@code 400 (Bad Request)} if the worklocationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the worklocationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/worklocations/{id}")
    public ResponseEntity<WorklocationDTO> updateWorklocation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody WorklocationDTO worklocationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Worklocation : {}, {}", id, worklocationDTO);
        if (worklocationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, worklocationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!worklocationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        WorklocationDTO result = worklocationService.save(worklocationDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, worklocationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /worklocations/:id} : Partial updates given fields of an existing worklocation, field will ignore if it is null
     *
     * @param id the id of the worklocationDTO to save.
     * @param worklocationDTO the worklocationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated worklocationDTO,
     * or with status {@code 400 (Bad Request)} if the worklocationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the worklocationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the worklocationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/worklocations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<WorklocationDTO> partialUpdateWorklocation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody WorklocationDTO worklocationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Worklocation partially : {}, {}", id, worklocationDTO);
        if (worklocationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, worklocationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!worklocationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<WorklocationDTO> result = worklocationService.partialUpdate(worklocationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, worklocationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /worklocations} : get all the worklocations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of worklocations in body.
     */
    @GetMapping("/worklocations")
    public List<WorklocationDTO> getAllWorklocations() {
        log.debug("REST request to get all Worklocations");
        return worklocationService.findAll();
    }

    /**
     * {@code GET  /worklocations/:id} : get the "id" worklocation.
     *
     * @param id the id of the worklocationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the worklocationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/worklocations/{id}")
    public ResponseEntity<WorklocationDTO> getWorklocation(@PathVariable Long id) {
        log.debug("REST request to get Worklocation : {}", id);
        Optional<WorklocationDTO> worklocationDTO = worklocationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(worklocationDTO);
    }

    /**
     * {@code DELETE  /worklocations/:id} : delete the "id" worklocation.
     *
     * @param id the id of the worklocationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/worklocations/{id}")
    public ResponseEntity<Void> deleteWorklocation(@PathVariable Long id) {
        log.debug("REST request to delete Worklocation : {}", id);
        worklocationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
