package com.contexity.whh.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EntryTypeMapperTest {

    private EntryTypeMapper entryTypeMapper;

    @BeforeEach
    public void setUp() {
        entryTypeMapper = new EntryTypeMapperImpl();
    }
}
