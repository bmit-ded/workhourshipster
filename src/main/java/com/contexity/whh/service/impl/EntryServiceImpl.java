package com.contexity.whh.service.impl;

import com.contexity.whh.domain.Entry;
import com.contexity.whh.repository.EntryRepository;
import com.contexity.whh.service.EntryService;
import com.contexity.whh.service.dto.EntryDTO;
import com.contexity.whh.service.mapper.EntryMapper;
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
 * Service Implementation for managing {@link Entry}.
 */
@Service
@Transactional
public class EntryServiceImpl implements EntryService {

    private final Logger log = LoggerFactory.getLogger(EntryServiceImpl.class);

    private final EntryRepository entryRepository;

    private final EntryMapper entryMapper;

    public EntryServiceImpl(EntryRepository entryRepository, EntryMapper entryMapper) {
        this.entryRepository = entryRepository;
        this.entryMapper = entryMapper;
    }

    @Override
    public EntryDTO save(EntryDTO entryDTO) {
        log.debug("Request to save Entry : {}", entryDTO);
        Entry entry = entryMapper.toEntity(entryDTO);
        entry = entryRepository.save(entry);
        return entryMapper.toDto(entry);
    }

    @Override
    public Optional<EntryDTO> partialUpdate(EntryDTO entryDTO) {
        log.debug("Request to partially update Entry : {}", entryDTO);

        return entryRepository
            .findById(entryDTO.getId())
            .map(existingEntry -> {
                entryMapper.partialUpdate(existingEntry, entryDTO);

                return existingEntry;
            })
            .map(entryRepository::save)
            .map(entryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EntryDTO> findAll() {
        log.debug("Request to get all Entries");
        return entryRepository
            .findAllWithEagerRelationships()
            .stream()
            .map(entryMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    public Page<EntryDTO> findAllWithEagerRelationships(Pageable pageable) {
        return entryRepository.findAllWithEagerRelationships(pageable).map(entryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EntryDTO> findOne(Long id) {
        log.debug("Request to get Entry : {}", id);
        return entryRepository.findOneWithEagerRelationships(id).map(entryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Entry : {}", id);
        entryRepository.deleteById(id);
    }
}
