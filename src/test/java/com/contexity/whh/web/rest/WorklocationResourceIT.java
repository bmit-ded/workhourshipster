package com.contexity.whh.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.contexity.whh.IntegrationTest;
import com.contexity.whh.domain.Worklocation;
import com.contexity.whh.repository.WorklocationRepository;
import com.contexity.whh.service.dto.WorklocationDTO;
import com.contexity.whh.service.mapper.WorklocationMapper;
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
 * Integration tests for the {@link WorklocationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WorklocationResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/worklocations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private WorklocationRepository worklocationRepository;

    @Autowired
    private WorklocationMapper worklocationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWorklocationMockMvc;

    private Worklocation worklocation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Worklocation createEntity(EntityManager em) {
        Worklocation worklocation = new Worklocation().name(DEFAULT_NAME);
        return worklocation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Worklocation createUpdatedEntity(EntityManager em) {
        Worklocation worklocation = new Worklocation().name(UPDATED_NAME);
        return worklocation;
    }

    @BeforeEach
    public void initTest() {
        worklocation = createEntity(em);
    }

    @Test
    @Transactional
    void createWorklocation() throws Exception {
        int databaseSizeBeforeCreate = worklocationRepository.findAll().size();
        // Create the Worklocation
        WorklocationDTO worklocationDTO = worklocationMapper.toDto(worklocation);
        restWorklocationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(worklocationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Worklocation in the database
        List<Worklocation> worklocationList = worklocationRepository.findAll();
        assertThat(worklocationList).hasSize(databaseSizeBeforeCreate + 1);
        Worklocation testWorklocation = worklocationList.get(worklocationList.size() - 1);
        assertThat(testWorklocation.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createWorklocationWithExistingId() throws Exception {
        // Create the Worklocation with an existing ID
        worklocation.setId(1L);
        WorklocationDTO worklocationDTO = worklocationMapper.toDto(worklocation);

        int databaseSizeBeforeCreate = worklocationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorklocationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(worklocationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Worklocation in the database
        List<Worklocation> worklocationList = worklocationRepository.findAll();
        assertThat(worklocationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWorklocations() throws Exception {
        // Initialize the database
        worklocationRepository.saveAndFlush(worklocation);

        // Get all the worklocationList
        restWorklocationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(worklocation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getWorklocation() throws Exception {
        // Initialize the database
        worklocationRepository.saveAndFlush(worklocation);

        // Get the worklocation
        restWorklocationMockMvc
            .perform(get(ENTITY_API_URL_ID, worklocation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(worklocation.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingWorklocation() throws Exception {
        // Get the worklocation
        restWorklocationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewWorklocation() throws Exception {
        // Initialize the database
        worklocationRepository.saveAndFlush(worklocation);

        int databaseSizeBeforeUpdate = worklocationRepository.findAll().size();

        // Update the worklocation
        Worklocation updatedWorklocation = worklocationRepository.findById(worklocation.getId()).get();
        // Disconnect from session so that the updates on updatedWorklocation are not directly saved in db
        em.detach(updatedWorklocation);
        updatedWorklocation.name(UPDATED_NAME);
        WorklocationDTO worklocationDTO = worklocationMapper.toDto(updatedWorklocation);

        restWorklocationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, worklocationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(worklocationDTO))
            )
            .andExpect(status().isOk());

        // Validate the Worklocation in the database
        List<Worklocation> worklocationList = worklocationRepository.findAll();
        assertThat(worklocationList).hasSize(databaseSizeBeforeUpdate);
        Worklocation testWorklocation = worklocationList.get(worklocationList.size() - 1);
        assertThat(testWorklocation.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingWorklocation() throws Exception {
        int databaseSizeBeforeUpdate = worklocationRepository.findAll().size();
        worklocation.setId(count.incrementAndGet());

        // Create the Worklocation
        WorklocationDTO worklocationDTO = worklocationMapper.toDto(worklocation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorklocationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, worklocationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(worklocationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Worklocation in the database
        List<Worklocation> worklocationList = worklocationRepository.findAll();
        assertThat(worklocationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWorklocation() throws Exception {
        int databaseSizeBeforeUpdate = worklocationRepository.findAll().size();
        worklocation.setId(count.incrementAndGet());

        // Create the Worklocation
        WorklocationDTO worklocationDTO = worklocationMapper.toDto(worklocation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorklocationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(worklocationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Worklocation in the database
        List<Worklocation> worklocationList = worklocationRepository.findAll();
        assertThat(worklocationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWorklocation() throws Exception {
        int databaseSizeBeforeUpdate = worklocationRepository.findAll().size();
        worklocation.setId(count.incrementAndGet());

        // Create the Worklocation
        WorklocationDTO worklocationDTO = worklocationMapper.toDto(worklocation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorklocationMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(worklocationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Worklocation in the database
        List<Worklocation> worklocationList = worklocationRepository.findAll();
        assertThat(worklocationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWorklocationWithPatch() throws Exception {
        // Initialize the database
        worklocationRepository.saveAndFlush(worklocation);

        int databaseSizeBeforeUpdate = worklocationRepository.findAll().size();

        // Update the worklocation using partial update
        Worklocation partialUpdatedWorklocation = new Worklocation();
        partialUpdatedWorklocation.setId(worklocation.getId());

        restWorklocationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorklocation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorklocation))
            )
            .andExpect(status().isOk());

        // Validate the Worklocation in the database
        List<Worklocation> worklocationList = worklocationRepository.findAll();
        assertThat(worklocationList).hasSize(databaseSizeBeforeUpdate);
        Worklocation testWorklocation = worklocationList.get(worklocationList.size() - 1);
        assertThat(testWorklocation.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateWorklocationWithPatch() throws Exception {
        // Initialize the database
        worklocationRepository.saveAndFlush(worklocation);

        int databaseSizeBeforeUpdate = worklocationRepository.findAll().size();

        // Update the worklocation using partial update
        Worklocation partialUpdatedWorklocation = new Worklocation();
        partialUpdatedWorklocation.setId(worklocation.getId());

        partialUpdatedWorklocation.name(UPDATED_NAME);

        restWorklocationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorklocation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorklocation))
            )
            .andExpect(status().isOk());

        // Validate the Worklocation in the database
        List<Worklocation> worklocationList = worklocationRepository.findAll();
        assertThat(worklocationList).hasSize(databaseSizeBeforeUpdate);
        Worklocation testWorklocation = worklocationList.get(worklocationList.size() - 1);
        assertThat(testWorklocation.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingWorklocation() throws Exception {
        int databaseSizeBeforeUpdate = worklocationRepository.findAll().size();
        worklocation.setId(count.incrementAndGet());

        // Create the Worklocation
        WorklocationDTO worklocationDTO = worklocationMapper.toDto(worklocation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorklocationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, worklocationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(worklocationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Worklocation in the database
        List<Worklocation> worklocationList = worklocationRepository.findAll();
        assertThat(worklocationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWorklocation() throws Exception {
        int databaseSizeBeforeUpdate = worklocationRepository.findAll().size();
        worklocation.setId(count.incrementAndGet());

        // Create the Worklocation
        WorklocationDTO worklocationDTO = worklocationMapper.toDto(worklocation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorklocationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(worklocationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Worklocation in the database
        List<Worklocation> worklocationList = worklocationRepository.findAll();
        assertThat(worklocationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWorklocation() throws Exception {
        int databaseSizeBeforeUpdate = worklocationRepository.findAll().size();
        worklocation.setId(count.incrementAndGet());

        // Create the Worklocation
        WorklocationDTO worklocationDTO = worklocationMapper.toDto(worklocation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorklocationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(worklocationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Worklocation in the database
        List<Worklocation> worklocationList = worklocationRepository.findAll();
        assertThat(worklocationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWorklocation() throws Exception {
        // Initialize the database
        worklocationRepository.saveAndFlush(worklocation);

        int databaseSizeBeforeDelete = worklocationRepository.findAll().size();

        // Delete the worklocation
        restWorklocationMockMvc
            .perform(delete(ENTITY_API_URL_ID, worklocation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Worklocation> worklocationList = worklocationRepository.findAll();
        assertThat(worklocationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
