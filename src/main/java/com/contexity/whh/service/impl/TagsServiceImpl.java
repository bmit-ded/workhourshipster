package com.contexity.whh.service.impl;

import com.contexity.whh.domain.Tags;
import com.contexity.whh.repository.TagsRepository;
import com.contexity.whh.service.TagsService;
import com.contexity.whh.service.dto.TagsDTO;
import com.contexity.whh.service.mapper.TagsMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Tags}.
 */
@Service
@Transactional
public class TagsServiceImpl implements TagsService {

    private final Logger log = LoggerFactory.getLogger(TagsServiceImpl.class);

    private final TagsRepository tagsRepository;

    private final TagsMapper tagsMapper;

    public TagsServiceImpl(TagsRepository tagsRepository, TagsMapper tagsMapper) {
        this.tagsRepository = tagsRepository;
        this.tagsMapper = tagsMapper;
    }

    @Override
    public TagsDTO save(TagsDTO tagsDTO) {
        log.debug("Request to save Tags : {}", tagsDTO);
        Tags tags = tagsMapper.toEntity(tagsDTO);
        tags = tagsRepository.save(tags);
        return tagsMapper.toDto(tags);
    }

    @Override
    public Optional<TagsDTO> partialUpdate(TagsDTO tagsDTO) {
        log.debug("Request to partially update Tags : {}", tagsDTO);

        return tagsRepository
            .findById(tagsDTO.getId())
            .map(existingTags -> {
                tagsMapper.partialUpdate(existingTags, tagsDTO);

                return existingTags;
            })
            .map(tagsRepository::save)
            .map(tagsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TagsDTO> findAll() {
        log.debug("Request to get all Tags");
        return tagsRepository
            .findAllWithEagerRelationships()
            .stream()
            .map(tagsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    public Page<TagsDTO> findAllWithEagerRelationships(Pageable pageable) {
        return tagsRepository.findAllWithEagerRelationships(pageable).map(tagsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TagsDTO> findOne(Long id) {
        log.debug("Request to get Tags : {}", id);
        return tagsRepository.findOneWithEagerRelationships(id).map(tagsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Tags : {}", id);
        tagsRepository.deleteById(id);
    }
}
