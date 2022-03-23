package com.contexity.whh.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WorklocationMapperTest {

    private WorklocationMapper worklocationMapper;

    @BeforeEach
    public void setUp() {
        worklocationMapper = new WorklocationMapperImpl();
    }
}
