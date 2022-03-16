package com.contexity.whh.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WorksheetMapperTest {

    private WorksheetMapper worksheetMapper;

    @BeforeEach
    public void setUp() {
        worksheetMapper = new WorksheetMapperImpl();
    }
}
