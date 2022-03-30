package com.contexity.whh.web.rest;

import com.contexity.whh.domain.Entry;
import com.contexity.whh.repository.EntryRepository;
import com.contexity.whh.service.EntryService;
import com.contexity.whh.service.dto.EntryDTO;
import com.contexity.whh.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Date;
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
 * REST controller for managing {@link com.contexity.whh.domain.Entry}.
 */
@RestController
@RequestMapping("/api")
public class EntryResource {

    private final Logger log = LoggerFactory.getLogger(EntryResource.class);

    private static final String ENTITY_NAME = "entry";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EntryService entryService;

    private final EntryRepository entryRepository;

    public EntryResource(EntryService entryService, EntryRepository entryRepository) {
        this.entryService = entryService;
        this.entryRepository = entryRepository;
    }

    /**
     * {@code POST  /entries} : Create a new entry.
     *
     * @param entryDTO the entryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new entryDTO, or with status {@code 400 (Bad Request)} if the entry has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/entries")
    public ResponseEntity<EntryDTO> createEntry(@Valid @RequestBody EntryDTO entryDTO) throws URISyntaxException {
        log.debug("REST request to save Entry : {}", entryDTO);
        if (entryDTO.getId() != null) {
            throw new BadRequestAlertException("A new entry cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EntryDTO result = entryService.save(entryDTO);
        return ResponseEntity
            .created(new URI("/api/entries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /entries/:id} : Updates an existing entry.
     *
     * @param id the id of the entryDTO to save.
     * @param entryDTO the entryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entryDTO,
     * or with status {@code 400 (Bad Request)} if the entryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the entryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/entries/{id}")
    public ResponseEntity<EntryDTO> updateEntry(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EntryDTO entryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Entry : {}, {}", id, entryDTO);
        if (entryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, entryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!entryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EntryDTO result = entryService.save(entryDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, entryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /entries/:id} : Partial updates given fields of an existing entry, field will ignore if it is null
     *
     * @param id the id of the entryDTO to save.
     * @param entryDTO the entryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entryDTO,
     * or with status {@code 400 (Bad Request)} if the entryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the entryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the entryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/entries/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EntryDTO> partialUpdateEntry(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EntryDTO entryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Entry partially : {}, {}", id, entryDTO);
        if (entryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, entryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!entryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EntryDTO> result = entryService.partialUpdate(entryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, entryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /entries} : get all the entries.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entries in body.
     */
    @GetMapping("/entries")
    public List<EntryDTO> getAllEntries(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Entries");
        return entryService.findAll();
    }

    /**
     * {@code GET  /entries/:id} : get the "id" entry.
     *
     * @param id the id of the entryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the entryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/entries/{id}")
    public ResponseEntity<EntryDTO> getEntry(@PathVariable Long id) {
        log.debug("REST request to get Entry : {}", id);
        Optional<EntryDTO> entryDTO = entryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(entryDTO);
    }

    /**
     * {@code DELETE  /entries/:id} : delete the "id" entry.
     *
     * @param id the id of the entryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/entries/{id}")
    public ResponseEntity<Void> deleteEntry(@PathVariable Long id) {
        log.debug("REST request to delete Entry : {}", id);
        entryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    //my work

    public List<Entry> findAllEntriesforProject(Long id) {
        List<Entry> entryList = entryRepository.findbyProject(id);
        return entryList;
    }

    public List<Entry> findDatedEntriesforProject(Long id, int month, int year) {
        List<Entry> entryList = entryRepository.findbyProjectandDate(id, month, year);

        return entryList;
    }

    public List<Entry> findWeeklyEntriesforProject(Long id, LocalDate date1, LocalDate date2) {
        List<Entry> entryList = entryRepository.findbyProjectandDateWeek(id, date1, date2);

        return entryList;
    }

    public double calculateHours(List<Entry> entryList) {
        double hours = 0;
        for (Entry entry : entryList) {
            hours = hours + entry.getHours();
        }
        return hours;
    }
}
