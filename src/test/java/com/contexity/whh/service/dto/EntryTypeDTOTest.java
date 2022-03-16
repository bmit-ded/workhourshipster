package com.contexity.whh.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.contexity.whh.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EntryTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EntryTypeDTO.class);
        EntryTypeDTO entryTypeDTO1 = new EntryTypeDTO();
        entryTypeDTO1.setId(1L);
        EntryTypeDTO entryTypeDTO2 = new EntryTypeDTO();
        assertThat(entryTypeDTO1).isNotEqualTo(entryTypeDTO2);
        entryTypeDTO2.setId(entryTypeDTO1.getId());
        assertThat(entryTypeDTO1).isEqualTo(entryTypeDTO2);
        entryTypeDTO2.setId(2L);
        assertThat(entryTypeDTO1).isNotEqualTo(entryTypeDTO2);
        entryTypeDTO1.setId(null);
        assertThat(entryTypeDTO1).isNotEqualTo(entryTypeDTO2);
    }
}
