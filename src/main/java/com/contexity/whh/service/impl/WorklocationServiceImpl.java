package com.contexity.whh.service.impl;

import com.contexity.whh.domain.Worklocation;
import com.contexity.whh.repository.WorklocationRepository;
import com.contexity.whh.service.WorklocationService;
import com.contexity.whh.service.dto.WorklocationDTO;
import com.contexity.whh.service.mapper.WorklocationMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Worklocation}.
 */
@Service
@Transactional
public class WorklocationServiceImpl implements WorklocationService {

    private final Logger log = LoggerFactory.getLogger(WorklocationServiceImpl.class);

    private final WorklocationRepository worklocationRepository;

    private final WorklocationMapper worklocationMapper;

    public WorklocationServiceImpl(WorklocationRepository worklocationRepository, WorklocationMapper worklocationMapper) {
        this.worklocationRepository = worklocationRepository;
        this.worklocationMapper = worklocationMapper;
    }

    @Override
    public WorklocationDTO save(WorklocationDTO worklocationDTO) {
        log.debug("Request to save Worklocation : {}", worklocationDTO);
        Worklocation worklocation = worklocationMapper.toEntity(worklocationDTO);
        worklocation = worklocationRepository.save(worklocation);
        return worklocationMapper.toDto(worklocation);
    }

    @Override
    public Optional<WorklocationDTO> partialUpdate(WorklocationDTO worklocationDTO) {
        log.debug("Request to partially update Worklocation : {}", worklocationDTO);

        return worklocationRepository
            .findById(worklocationDTO.getId())
            .map(existingWorklocation -> {
                worklocationMapper.partialUpdate(existingWorklocation, worklocationDTO);

                return existingWorklocation;
            })
            .map(worklocationRepository::save)
            .map(worklocationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorklocationDTO> findAll() {
        log.debug("Request to get all Worklocations");
        return worklocationRepository.findAll().stream().map(worklocationMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WorklocationDTO> findOne(Long id) {
        log.debug("Request to get Worklocation : {}", id);
        return worklocationRepository.findById(id).map(worklocationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Worklocation : {}", id);
        worklocationRepository.deleteById(id);
    }
}
