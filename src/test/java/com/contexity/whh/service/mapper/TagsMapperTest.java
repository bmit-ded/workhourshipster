package com.contexity.whh.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TagsMapperTest {

    private TagsMapper tagsMapper;

    @BeforeEach
    public void setUp() {
        tagsMapper = new TagsMapperImpl();
    }
}
