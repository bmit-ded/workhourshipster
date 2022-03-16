package com.contexity.whh.service.impl;

import com.contexity.whh.domain.Worksheet;
import com.contexity.whh.repository.WorksheetRepository;
import com.contexity.whh.service.WorksheetService;
import com.contexity.whh.service.dto.WorksheetDTO;
import com.contexity.whh.service.mapper.WorksheetMapper;
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
 * Service Implementation for managing {@link Worksheet}.
 */
@Service
@Transactional
public class WorksheetServiceImpl implements WorksheetService {

    private final Logger log = LoggerFactory.getLogger(WorksheetServiceImpl.class);

    private final WorksheetRepository worksheetRepository;

    private final WorksheetMapper worksheetMapper;

    public WorksheetServiceImpl(WorksheetRepository worksheetRepository, WorksheetMapper worksheetMapper) {
        this.worksheetRepository = worksheetRepository;
        this.worksheetMapper = worksheetMapper;
    }

    @Override
    public WorksheetDTO save(WorksheetDTO worksheetDTO) {
        log.debug("Request to save Worksheet : {}", worksheetDTO);
        Worksheet worksheet = worksheetMapper.toEntity(worksheetDTO);
        worksheet = worksheetRepository.save(worksheet);
        return worksheetMapper.toDto(worksheet);
    }

    @Override
    public Optional<WorksheetDTO> partialUpdate(WorksheetDTO worksheetDTO) {
        log.debug("Request to partially update Worksheet : {}", worksheetDTO);

        return worksheetRepository
            .findById(worksheetDTO.getId())
            .map(existingWorksheet -> {
                worksheetMapper.partialUpdate(existingWorksheet, worksheetDTO);

                return existingWorksheet;
            })
            .map(worksheetRepository::save)
            .map(worksheetMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorksheetDTO> findAll() {
        log.debug("Request to get all Worksheets");
        return worksheetRepository
            .findAllWithEagerRelationships()
            .stream()
            .map(worksheetMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    public Page<WorksheetDTO> findAllWithEagerRelationships(Pageable pageable) {
        return worksheetRepository.findAllWithEagerRelationships(pageable).map(worksheetMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WorksheetDTO> findOne(Long id) {
        log.debug("Request to get Worksheet : {}", id);
        return worksheetRepository.findOneWithEagerRelationships(id).map(worksheetMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Worksheet : {}", id);
        worksheetRepository.deleteById(id);
    }
}
