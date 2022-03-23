package com.contexity.whh.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.contexity.whh.IntegrationTest;
import com.contexity.whh.domain.Tags;
import com.contexity.whh.repository.TagsRepository;
import com.contexity.whh.service.TagsService;
import com.contexity.whh.service.dto.TagsDTO;
import com.contexity.whh.service.mapper.TagsMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TagsResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class TagsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tags";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TagsRepository tagsRepository;

    @Mock
    private TagsRepository tagsRepositoryMock;

    @Autowired
    private TagsMapper tagsMapper;

    @Mock
    private TagsService tagsServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTagsMockMvc;

    private Tags tags;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tags createEntity(EntityManager em) {
        Tags tags = new Tags().name(DEFAULT_NAME);
        return tags;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tags createUpdatedEntity(EntityManager em) {
        Tags tags = new Tags().name(UPDATED_NAME);
        return tags;
    }

    @BeforeEach
    public void initTest() {
        tags = createEntity(em);
    }

    @Test
    @Transactional
    void createTags() throws Exception {
        int databaseSizeBeforeCreate = tagsRepository.findAll().size();
        // Create the Tags
        TagsDTO tagsDTO = tagsMapper.toDto(tags);
        restTagsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tagsDTO)))
            .andExpect(status().isCreated());

        // Validate the Tags in the database
        List<Tags> tagsList = tagsRepository.findAll();
        assertThat(tagsList).hasSize(databaseSizeBeforeCreate + 1);
        Tags testTags = tagsList.get(tagsList.size() - 1);
        assertThat(testTags.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createTagsWithExistingId() throws Exception {
        // Create the Tags with an existing ID
        tags.setId(1L);
        TagsDTO tagsDTO = tagsMapper.toDto(tags);

        int databaseSizeBeforeCreate = tagsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTagsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tagsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tags in the database
        List<Tags> tagsList = tagsRepository.findAll();
        assertThat(tagsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = tagsRepository.findAll().size();
        // set the field null
        tags.setName(null);

        // Create the Tags, which fails.
        TagsDTO tagsDTO = tagsMapper.toDto(tags);

        restTagsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tagsDTO)))
            .andExpect(status().isBadRequest());

        List<Tags> tagsList = tagsRepository.findAll();
        assertThat(tagsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTags() throws Exception {
        // Initialize the database
        tagsRepository.saveAndFlush(tags);

        // Get all the tagsList
        restTagsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tags.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTagsWithEagerRelationshipsIsEnabled() throws Exception {
        when(tagsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTagsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(tagsServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTagsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(tagsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTagsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(tagsServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getTags() throws Exception {
        // Initialize the database
        tagsRepository.saveAndFlush(tags);

        // Get the tags
        restTagsMockMvc
            .perform(get(ENTITY_API_URL_ID, tags.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tags.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingTags() throws Exception {
        // Get the tags
        restTagsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTags() throws Exception {
        // Initialize the database
        tagsRepository.saveAndFlush(tags);

        int databaseSizeBeforeUpdate = tagsRepository.findAll().size();

        // Update the tags
        Tags updatedTags = tagsRepository.findById(tags.getId()).get();
        // Disconnect from session so that the updates on updatedTags are not directly saved in db
        em.detach(updatedTags);
        updatedTags.name(UPDATED_NAME);
        TagsDTO tagsDTO = tagsMapper.toDto(updatedTags);

        restTagsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tagsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tagsDTO))
            )
            .andExpect(status().isOk());

        // Validate the Tags in the database
        List<Tags> tagsList = tagsRepository.findAll();
        assertThat(tagsList).hasSize(databaseSizeBeforeUpdate);
        Tags testTags = tagsList.get(tagsList.size() - 1);
        assertThat(testTags.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingTags() throws Exception {
        int databaseSizeBeforeUpdate = tagsRepository.findAll().size();
        tags.setId(count.incrementAndGet());

        // Create the Tags
        TagsDTO tagsDTO = tagsMapper.toDto(tags);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTagsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tagsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tagsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tags in the database
        List<Tags> tagsList = tagsRepository.findAll();
        assertThat(tagsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTags() throws Exception {
        int databaseSizeBeforeUpdate = tagsRepository.findAll().size();
        tags.setId(count.incrementAndGet());

        // Create the Tags
        TagsDTO tagsDTO = tagsMapper.toDto(tags);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTagsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tagsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tags in the database
        List<Tags> tagsList = tagsRepository.findAll();
        assertThat(tagsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTags() throws Exception {
        int databaseSizeBeforeUpdate = tagsRepository.findAll().size();
        tags.setId(count.incrementAndGet());

        // Create the Tags
        TagsDTO tagsDTO = tagsMapper.toDto(tags);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTagsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tagsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tags in the database
        List<Tags> tagsList = tagsRepository.findAll();
        assertThat(tagsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTagsWithPatch() throws Exception {
        // Initialize the database
        tagsRepository.saveAndFlush(tags);

        int databaseSizeBeforeUpdate = tagsRepository.findAll().size();

        // Update the tags using partial update
        Tags partialUpdatedTags = new Tags();
        partialUpdatedTags.setId(tags.getId());

        restTagsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTags.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTags))
            )
            .andExpect(status().isOk());

        // Validate the Tags in the database
        List<Tags> tagsList = tagsRepository.findAll();
        assertThat(tagsList).hasSize(databaseSizeBeforeUpdate);
        Tags testTags = tagsList.get(tagsList.size() - 1);
        assertThat(testTags.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateTagsWithPatch() throws Exception {
        // Initialize the database
        tagsRepository.saveAndFlush(tags);

        int databaseSizeBeforeUpdate = tagsRepository.findAll().size();

        // Update the tags using partial update
        Tags partialUpdatedTags = new Tags();
        partialUpdatedTags.setId(tags.getId());

        partialUpdatedTags.name(UPDATED_NAME);

        restTagsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTags.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTags))
            )
            .andExpect(status().isOk());

        // Validate the Tags in the database
        List<Tags> tagsList = tagsRepository.findAll();
        assertThat(tagsList).hasSize(databaseSizeBeforeUpdate);
        Tags testTags = tagsList.get(tagsList.size() - 1);
        assertThat(testTags.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingTags() throws Exception {
        int databaseSizeBeforeUpdate = tagsRepository.findAll().size();
        tags.setId(count.incrementAndGet());

        // Create the Tags
        TagsDTO tagsDTO = tagsMapper.toDto(tags);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTagsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tagsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tagsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tags in the database
        List<Tags> tagsList = tagsRepository.findAll();
        assertThat(tagsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTags() throws Exception {
        int databaseSizeBeforeUpdate = tagsRepository.findAll().size();
        tags.setId(count.incrementAndGet());

        // Create the Tags
        TagsDTO tagsDTO = tagsMapper.toDto(tags);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTagsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tagsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tags in the database
        List<Tags> tagsList = tagsRepository.findAll();
        assertThat(tagsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTags() throws Exception {
        int databaseSizeBeforeUpdate = tagsRepository.findAll().size();
        tags.setId(count.incrementAndGet());

        // Create the Tags
        TagsDTO tagsDTO = tagsMapper.toDto(tags);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTagsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tagsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tags in the database
        List<Tags> tagsList = tagsRepository.findAll();
        assertThat(tagsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTags() throws Exception {
        // Initialize the database
        tagsRepository.saveAndFlush(tags);

        int databaseSizeBeforeDelete = tagsRepository.findAll().size();

        // Delete the tags
        restTagsMockMvc
            .perform(delete(ENTITY_API_URL_ID, tags.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Tags> tagsList = tagsRepository.findAll();
        assertThat(tagsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
