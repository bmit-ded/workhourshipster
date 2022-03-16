package com.contexity.whh.web.rest;

import com.contexity.whh.repository.WorksheetRepository;
import com.contexity.whh.service.WorksheetService;
import com.contexity.whh.service.dto.WorksheetDTO;
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
 * REST controller for managing {@link com.contexity.whh.domain.Worksheet}.
 */
@RestController
@RequestMapping("/api")
public class WorksheetResource {

    private final Logger log = LoggerFactory.getLogger(WorksheetResource.class);

    private static final String ENTITY_NAME = "worksheet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WorksheetService worksheetService;

    private final WorksheetRepository worksheetRepository;

    public WorksheetResource(WorksheetService worksheetService, WorksheetRepository worksheetRepository) {
        this.worksheetService = worksheetService;
        this.worksheetRepository = worksheetRepository;
    }

    /**
     * {@code POST  /worksheets} : Create a new worksheet.
     *
     * @param worksheetDTO the worksheetDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new worksheetDTO, or with status {@code 400 (Bad Request)} if the worksheet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/worksheets")
    public ResponseEntity<WorksheetDTO> createWorksheet(@RequestBody WorksheetDTO worksheetDTO) throws URISyntaxException {
        log.debug("REST request to save Worksheet : {}", worksheetDTO);
        if (worksheetDTO.getId() != null) {
            throw new BadRequestAlertException("A new worksheet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorksheetDTO result = worksheetService.save(worksheetDTO);
        return ResponseEntity
            .created(new URI("/api/worksheets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /worksheets/:id} : Updates an existing worksheet.
     *
     * @param id the id of the worksheetDTO to save.
     * @param worksheetDTO the worksheetDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated worksheetDTO,
     * or with status {@code 400 (Bad Request)} if the worksheetDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the worksheetDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/worksheets/{id}")
    public ResponseEntity<WorksheetDTO> updateWorksheet(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody WorksheetDTO worksheetDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Worksheet : {}, {}", id, worksheetDTO);
        if (worksheetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, worksheetDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!worksheetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        WorksheetDTO result = worksheetService.save(worksheetDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, worksheetDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /worksheets/:id} : Partial updates given fields of an existing worksheet, field will ignore if it is null
     *
     * @param id the id of the worksheetDTO to save.
     * @param worksheetDTO the worksheetDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated worksheetDTO,
     * or with status {@code 400 (Bad Request)} if the worksheetDTO is not valid,
     * or with status {@code 404 (Not Found)} if the worksheetDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the worksheetDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/worksheets/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<WorksheetDTO> partialUpdateWorksheet(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody WorksheetDTO worksheetDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Worksheet partially : {}, {}", id, worksheetDTO);
        if (worksheetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, worksheetDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!worksheetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<WorksheetDTO> result = worksheetService.partialUpdate(worksheetDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, worksheetDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /worksheets} : get all the worksheets.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of worksheets in body.
     */
    @GetMapping("/worksheets")
    public List<WorksheetDTO> getAllWorksheets(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Worksheets");
        return worksheetService.findAll();
    }

    /**
     * {@code GET  /worksheets/:id} : get the "id" worksheet.
     *
     * @param id the id of the worksheetDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the worksheetDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/worksheets/{id}")
    public ResponseEntity<WorksheetDTO> getWorksheet(@PathVariable Long id) {
        log.debug("REST request to get Worksheet : {}", id);
        Optional<WorksheetDTO> worksheetDTO = worksheetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(worksheetDTO);
    }

    /**
     * {@code DELETE  /worksheets/:id} : delete the "id" worksheet.
     *
     * @param id the id of the worksheetDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/worksheets/{id}")
    public ResponseEntity<Void> deleteWorksheet(@PathVariable Long id) {
        log.debug("REST request to delete Worksheet : {}", id);
        worksheetService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
