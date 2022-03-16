package com.contexity.whh.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.contexity.whh.IntegrationTest;
import com.contexity.whh.domain.Worksheet;
import com.contexity.whh.repository.WorksheetRepository;
import com.contexity.whh.service.dto.WorksheetDTO;
import com.contexity.whh.service.mapper.WorksheetMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link WorksheetResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WorksheetResourceIT {

    private static final String ENTITY_API_URL = "/api/worksheets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private WorksheetRepository worksheetRepository;

    @Autowired
    private WorksheetMapper worksheetMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWorksheetMockMvc;

    private Worksheet worksheet;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Worksheet createEntity(EntityManager em) {
        Worksheet worksheet = new Worksheet();
        return worksheet;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Worksheet createUpdatedEntity(EntityManager em) {
        Worksheet worksheet = new Worksheet();
        return worksheet;
    }

    @BeforeEach
    public void initTest() {
        worksheet = createEntity(em);
    }

    @Test
    @Transactional
    void createWorksheet() throws Exception {
        int databaseSizeBeforeCreate = worksheetRepository.findAll().size();
        // Create the Worksheet
        WorksheetDTO worksheetDTO = worksheetMapper.toDto(worksheet);
        restWorksheetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(worksheetDTO)))
            .andExpect(status().isCreated());

        // Validate the Worksheet in the database
        List<Worksheet> worksheetList = worksheetRepository.findAll();
        assertThat(worksheetList).hasSize(databaseSizeBeforeCreate + 1);
        Worksheet testWorksheet = worksheetList.get(worksheetList.size() - 1);
    }

    @Test
    @Transactional
    void createWorksheetWithExistingId() throws Exception {
        // Create the Worksheet with an existing ID
        worksheet.setId(1L);
        WorksheetDTO worksheetDTO = worksheetMapper.toDto(worksheet);

        int databaseSizeBeforeCreate = worksheetRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorksheetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(worksheetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Worksheet in the database
        List<Worksheet> worksheetList = worksheetRepository.findAll();
        assertThat(worksheetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWorksheets() throws Exception {
        // Initialize the database
        worksheetRepository.saveAndFlush(worksheet);

        // Get all the worksheetList
        restWorksheetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(worksheet.getId().intValue())));
    }

    @Test
    @Transactional
    void getWorksheet() throws Exception {
        // Initialize the database
        worksheetRepository.saveAndFlush(worksheet);

        // Get the worksheet
        restWorksheetMockMvc
            .perform(get(ENTITY_API_URL_ID, worksheet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(worksheet.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingWorksheet() throws Exception {
        // Get the worksheet
        restWorksheetMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewWorksheet() throws Exception {
        // Initialize the database
        worksheetRepository.saveAndFlush(worksheet);

        int databaseSizeBeforeUpdate = worksheetRepository.findAll().size();

        // Update the worksheet
        Worksheet updatedWorksheet = worksheetRepository.findById(worksheet.getId()).get();
        // Disconnect from session so that the updates on updatedWorksheet are not directly saved in db
        em.detach(updatedWorksheet);
        WorksheetDTO worksheetDTO = worksheetMapper.toDto(updatedWorksheet);

        restWorksheetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, worksheetDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(worksheetDTO))
            )
            .andExpect(status().isOk());

        // Validate the Worksheet in the database
        List<Worksheet> worksheetList = worksheetRepository.findAll();
        assertThat(worksheetList).hasSize(databaseSizeBeforeUpdate);
        Worksheet testWorksheet = worksheetList.get(worksheetList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingWorksheet() throws Exception {
        int databaseSizeBeforeUpdate = worksheetRepository.findAll().size();
        worksheet.setId(count.incrementAndGet());

        // Create the Worksheet
        WorksheetDTO worksheetDTO = worksheetMapper.toDto(worksheet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorksheetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, worksheetDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(worksheetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Worksheet in the database
        List<Worksheet> worksheetList = worksheetRepository.findAll();
        assertThat(worksheetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWorksheet() throws Exception {
        int databaseSizeBeforeUpdate = worksheetRepository.findAll().size();
        worksheet.setId(count.incrementAndGet());

        // Create the Worksheet
        WorksheetDTO worksheetDTO = worksheetMapper.toDto(worksheet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorksheetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(worksheetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Worksheet in the database
        List<Worksheet> worksheetList = worksheetRepository.findAll();
        assertThat(worksheetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWorksheet() throws Exception {
        int databaseSizeBeforeUpdate = worksheetRepository.findAll().size();
        worksheet.setId(count.incrementAndGet());

        // Create the Worksheet
        WorksheetDTO worksheetDTO = worksheetMapper.toDto(worksheet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorksheetMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(worksheetDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Worksheet in the database
        List<Worksheet> worksheetList = worksheetRepository.findAll();
        assertThat(worksheetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWorksheetWithPatch() throws Exception {
        // Initialize the database
        worksheetRepository.saveAndFlush(worksheet);

        int databaseSizeBeforeUpdate = worksheetRepository.findAll().size();

        // Update the worksheet using partial update
        Worksheet partialUpdatedWorksheet = new Worksheet();
        partialUpdatedWorksheet.setId(worksheet.getId());

        restWorksheetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorksheet.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorksheet))
            )
            .andExpect(status().isOk());

        // Validate the Worksheet in the database
        List<Worksheet> worksheetList = worksheetRepository.findAll();
        assertThat(worksheetList).hasSize(databaseSizeBeforeUpdate);
        Worksheet testWorksheet = worksheetList.get(worksheetList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateWorksheetWithPatch() throws Exception {
        // Initialize the database
        worksheetRepository.saveAndFlush(worksheet);

        int databaseSizeBeforeUpdate = worksheetRepository.findAll().size();

        // Update the worksheet using partial update
        Worksheet partialUpdatedWorksheet = new Worksheet();
        partialUpdatedWorksheet.setId(worksheet.getId());

        restWorksheetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorksheet.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorksheet))
            )
            .andExpect(status().isOk());

        // Validate the Worksheet in the database
        List<Worksheet> worksheetList = worksheetRepository.findAll();
        assertThat(worksheetList).hasSize(databaseSizeBeforeUpdate);
        Worksheet testWorksheet = worksheetList.get(worksheetList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingWorksheet() throws Exception {
        int databaseSizeBeforeUpdate = worksheetRepository.findAll().size();
        worksheet.setId(count.incrementAndGet());

        // Create the Worksheet
        WorksheetDTO worksheetDTO = worksheetMapper.toDto(worksheet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorksheetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, worksheetDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(worksheetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Worksheet in the database
        List<Worksheet> worksheetList = worksheetRepository.findAll();
        assertThat(worksheetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWorksheet() throws Exception {
        int databaseSizeBeforeUpdate = worksheetRepository.findAll().size();
        worksheet.setId(count.incrementAndGet());

        // Create the Worksheet
        WorksheetDTO worksheetDTO = worksheetMapper.toDto(worksheet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorksheetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(worksheetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Worksheet in the database
        List<Worksheet> worksheetList = worksheetRepository.findAll();
        assertThat(worksheetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWorksheet() throws Exception {
        int databaseSizeBeforeUpdate = worksheetRepository.findAll().size();
        worksheet.setId(count.incrementAndGet());

        // Create the Worksheet
        WorksheetDTO worksheetDTO = worksheetMapper.toDto(worksheet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorksheetMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(worksheetDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Worksheet in the database
        List<Worksheet> worksheetList = worksheetRepository.findAll();
        assertThat(worksheetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWorksheet() throws Exception {
        // Initialize the database
        worksheetRepository.saveAndFlush(worksheet);

        int databaseSizeBeforeDelete = worksheetRepository.findAll().size();

        // Delete the worksheet
        restWorksheetMockMvc
            .perform(delete(ENTITY_API_URL_ID, worksheet.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Worksheet> worksheetList = worksheetRepository.findAll();
        assertThat(worksheetList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
