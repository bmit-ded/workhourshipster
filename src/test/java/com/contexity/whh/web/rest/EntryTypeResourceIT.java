package com.contexity.whh.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.contexity.whh.IntegrationTest;
import com.contexity.whh.domain.EntryType;
import com.contexity.whh.repository.EntryTypeRepository;
import com.contexity.whh.service.dto.EntryTypeDTO;
import com.contexity.whh.service.mapper.EntryTypeMapper;
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
 * Integration tests for the {@link EntryTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EntryTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_WORKTIME = false;
    private static final Boolean UPDATED_WORKTIME = true;

    private static final String ENTITY_API_URL = "/api/entry-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EntryTypeRepository entryTypeRepository;

    @Autowired
    private EntryTypeMapper entryTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEntryTypeMockMvc;

    private EntryType entryType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EntryType createEntity(EntityManager em) {
        EntryType entryType = new EntryType().name(DEFAULT_NAME).worktime(DEFAULT_WORKTIME);
        return entryType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EntryType createUpdatedEntity(EntityManager em) {
        EntryType entryType = new EntryType().name(UPDATED_NAME).worktime(UPDATED_WORKTIME);
        return entryType;
    }

    @BeforeEach
    public void initTest() {
        entryType = createEntity(em);
    }

    @Test
    @Transactional
    void createEntryType() throws Exception {
        int databaseSizeBeforeCreate = entryTypeRepository.findAll().size();
        // Create the EntryType
        EntryTypeDTO entryTypeDTO = entryTypeMapper.toDto(entryType);
        restEntryTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entryTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the EntryType in the database
        List<EntryType> entryTypeList = entryTypeRepository.findAll();
        assertThat(entryTypeList).hasSize(databaseSizeBeforeCreate + 1);
        EntryType testEntryType = entryTypeList.get(entryTypeList.size() - 1);
        assertThat(testEntryType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEntryType.getWorktime()).isEqualTo(DEFAULT_WORKTIME);
    }

    @Test
    @Transactional
    void createEntryTypeWithExistingId() throws Exception {
        // Create the EntryType with an existing ID
        entryType.setId(1L);
        EntryTypeDTO entryTypeDTO = entryTypeMapper.toDto(entryType);

        int databaseSizeBeforeCreate = entryTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntryTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entryTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EntryType in the database
        List<EntryType> entryTypeList = entryTypeRepository.findAll();
        assertThat(entryTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = entryTypeRepository.findAll().size();
        // set the field null
        entryType.setName(null);

        // Create the EntryType, which fails.
        EntryTypeDTO entryTypeDTO = entryTypeMapper.toDto(entryType);

        restEntryTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entryTypeDTO)))
            .andExpect(status().isBadRequest());

        List<EntryType> entryTypeList = entryTypeRepository.findAll();
        assertThat(entryTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkWorktimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = entryTypeRepository.findAll().size();
        // set the field null
        entryType.setWorktime(null);

        // Create the EntryType, which fails.
        EntryTypeDTO entryTypeDTO = entryTypeMapper.toDto(entryType);

        restEntryTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entryTypeDTO)))
            .andExpect(status().isBadRequest());

        List<EntryType> entryTypeList = entryTypeRepository.findAll();
        assertThat(entryTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEntryTypes() throws Exception {
        // Initialize the database
        entryTypeRepository.saveAndFlush(entryType);

        // Get all the entryTypeList
        restEntryTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entryType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].worktime").value(hasItem(DEFAULT_WORKTIME.booleanValue())));
    }

    @Test
    @Transactional
    void getEntryType() throws Exception {
        // Initialize the database
        entryTypeRepository.saveAndFlush(entryType);

        // Get the entryType
        restEntryTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, entryType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(entryType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.worktime").value(DEFAULT_WORKTIME.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingEntryType() throws Exception {
        // Get the entryType
        restEntryTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEntryType() throws Exception {
        // Initialize the database
        entryTypeRepository.saveAndFlush(entryType);

        int databaseSizeBeforeUpdate = entryTypeRepository.findAll().size();

        // Update the entryType
        EntryType updatedEntryType = entryTypeRepository.findById(entryType.getId()).get();
        // Disconnect from session so that the updates on updatedEntryType are not directly saved in db
        em.detach(updatedEntryType);
        updatedEntryType.name(UPDATED_NAME).worktime(UPDATED_WORKTIME);
        EntryTypeDTO entryTypeDTO = entryTypeMapper.toDto(updatedEntryType);

        restEntryTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, entryTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(entryTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the EntryType in the database
        List<EntryType> entryTypeList = entryTypeRepository.findAll();
        assertThat(entryTypeList).hasSize(databaseSizeBeforeUpdate);
        EntryType testEntryType = entryTypeList.get(entryTypeList.size() - 1);
        assertThat(testEntryType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEntryType.getWorktime()).isEqualTo(UPDATED_WORKTIME);
    }

    @Test
    @Transactional
    void putNonExistingEntryType() throws Exception {
        int databaseSizeBeforeUpdate = entryTypeRepository.findAll().size();
        entryType.setId(count.incrementAndGet());

        // Create the EntryType
        EntryTypeDTO entryTypeDTO = entryTypeMapper.toDto(entryType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntryTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, entryTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(entryTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EntryType in the database
        List<EntryType> entryTypeList = entryTypeRepository.findAll();
        assertThat(entryTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEntryType() throws Exception {
        int databaseSizeBeforeUpdate = entryTypeRepository.findAll().size();
        entryType.setId(count.incrementAndGet());

        // Create the EntryType
        EntryTypeDTO entryTypeDTO = entryTypeMapper.toDto(entryType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntryTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(entryTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EntryType in the database
        List<EntryType> entryTypeList = entryTypeRepository.findAll();
        assertThat(entryTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEntryType() throws Exception {
        int databaseSizeBeforeUpdate = entryTypeRepository.findAll().size();
        entryType.setId(count.incrementAndGet());

        // Create the EntryType
        EntryTypeDTO entryTypeDTO = entryTypeMapper.toDto(entryType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntryTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entryTypeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EntryType in the database
        List<EntryType> entryTypeList = entryTypeRepository.findAll();
        assertThat(entryTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEntryTypeWithPatch() throws Exception {
        // Initialize the database
        entryTypeRepository.saveAndFlush(entryType);

        int databaseSizeBeforeUpdate = entryTypeRepository.findAll().size();

        // Update the entryType using partial update
        EntryType partialUpdatedEntryType = new EntryType();
        partialUpdatedEntryType.setId(entryType.getId());

        partialUpdatedEntryType.name(UPDATED_NAME).worktime(UPDATED_WORKTIME);

        restEntryTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEntryType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEntryType))
            )
            .andExpect(status().isOk());

        // Validate the EntryType in the database
        List<EntryType> entryTypeList = entryTypeRepository.findAll();
        assertThat(entryTypeList).hasSize(databaseSizeBeforeUpdate);
        EntryType testEntryType = entryTypeList.get(entryTypeList.size() - 1);
        assertThat(testEntryType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEntryType.getWorktime()).isEqualTo(UPDATED_WORKTIME);
    }

    @Test
    @Transactional
    void fullUpdateEntryTypeWithPatch() throws Exception {
        // Initialize the database
        entryTypeRepository.saveAndFlush(entryType);

        int databaseSizeBeforeUpdate = entryTypeRepository.findAll().size();

        // Update the entryType using partial update
        EntryType partialUpdatedEntryType = new EntryType();
        partialUpdatedEntryType.setId(entryType.getId());

        partialUpdatedEntryType.name(UPDATED_NAME).worktime(UPDATED_WORKTIME);

        restEntryTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEntryType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEntryType))
            )
            .andExpect(status().isOk());

        // Validate the EntryType in the database
        List<EntryType> entryTypeList = entryTypeRepository.findAll();
        assertThat(entryTypeList).hasSize(databaseSizeBeforeUpdate);
        EntryType testEntryType = entryTypeList.get(entryTypeList.size() - 1);
        assertThat(testEntryType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEntryType.getWorktime()).isEqualTo(UPDATED_WORKTIME);
    }

    @Test
    @Transactional
    void patchNonExistingEntryType() throws Exception {
        int databaseSizeBeforeUpdate = entryTypeRepository.findAll().size();
        entryType.setId(count.incrementAndGet());

        // Create the EntryType
        EntryTypeDTO entryTypeDTO = entryTypeMapper.toDto(entryType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntryTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, entryTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(entryTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EntryType in the database
        List<EntryType> entryTypeList = entryTypeRepository.findAll();
        assertThat(entryTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEntryType() throws Exception {
        int databaseSizeBeforeUpdate = entryTypeRepository.findAll().size();
        entryType.setId(count.incrementAndGet());

        // Create the EntryType
        EntryTypeDTO entryTypeDTO = entryTypeMapper.toDto(entryType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntryTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(entryTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EntryType in the database
        List<EntryType> entryTypeList = entryTypeRepository.findAll();
        assertThat(entryTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEntryType() throws Exception {
        int databaseSizeBeforeUpdate = entryTypeRepository.findAll().size();
        entryType.setId(count.incrementAndGet());

        // Create the EntryType
        EntryTypeDTO entryTypeDTO = entryTypeMapper.toDto(entryType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntryTypeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(entryTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EntryType in the database
        List<EntryType> entryTypeList = entryTypeRepository.findAll();
        assertThat(entryTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEntryType() throws Exception {
        // Initialize the database
        entryTypeRepository.saveAndFlush(entryType);

        int databaseSizeBeforeDelete = entryTypeRepository.findAll().size();

        // Delete the entryType
        restEntryTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, entryType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EntryType> entryTypeList = entryTypeRepository.findAll();
        assertThat(entryTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
