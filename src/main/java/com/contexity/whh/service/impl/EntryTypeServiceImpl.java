package com.contexity.whh.service.impl;

import com.contexity.whh.domain.EntryType;
import com.contexity.whh.repository.EntryTypeRepository;
import com.contexity.whh.service.EntryTypeService;
import com.contexity.whh.service.dto.EntryTypeDTO;
import com.contexity.whh.service.mapper.EntryTypeMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EntryType}.
 */
@Service
@Transactional
public class EntryTypeServiceImpl implements EntryTypeService {

    private final Logger log = LoggerFactory.getLogger(EntryTypeServiceImpl.class);

    private final EntryTypeRepository entryTypeRepository;

    private final EntryTypeMapper entryTypeMapper;

    public EntryTypeServiceImpl(EntryTypeRepository entryTypeRepository, EntryTypeMapper entryTypeMapper) {
        this.entryTypeRepository = entryTypeRepository;
        this.entryTypeMapper = entryTypeMapper;
    }

    @Override
    public EntryTypeDTO save(EntryTypeDTO entryTypeDTO) {
        log.debug("Request to save EntryType : {}", entryTypeDTO);
        EntryType entryType = entryTypeMapper.toEntity(entryTypeDTO);
        entryType = entryTypeRepository.save(entryType);
        return entryTypeMapper.toDto(entryType);
    }

    @Override
    public Optional<EntryTypeDTO> partialUpdate(EntryTypeDTO entryTypeDTO) {
        log.debug("Request to partially update EntryType : {}", entryTypeDTO);

        return entryTypeRepository
            .findById(entryTypeDTO.getId())
            .map(existingEntryType -> {
                entryTypeMapper.partialUpdate(existingEntryType, entryTypeDTO);

                return existingEntryType;
            })
            .map(entryTypeRepository::save)
            .map(entryTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EntryTypeDTO> findAll() {
        log.debug("Request to get all EntryTypes");
        return entryTypeRepository.findAll().stream().map(entryTypeMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EntryTypeDTO> findOne(Long id) {
        log.debug("Request to get EntryType : {}", id);
        return entryTypeRepository.findById(id).map(entryTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EntryType : {}", id);
        entryTypeRepository.deleteById(id);
    }
}
