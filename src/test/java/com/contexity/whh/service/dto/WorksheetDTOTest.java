package com.contexity.whh.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.contexity.whh.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WorksheetDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorksheetDTO.class);
        WorksheetDTO worksheetDTO1 = new WorksheetDTO();
        worksheetDTO1.setId(1L);
        WorksheetDTO worksheetDTO2 = new WorksheetDTO();
        assertThat(worksheetDTO1).isNotEqualTo(worksheetDTO2);
        worksheetDTO2.setId(worksheetDTO1.getId());
        assertThat(worksheetDTO1).isEqualTo(worksheetDTO2);
        worksheetDTO2.setId(2L);
        assertThat(worksheetDTO1).isNotEqualTo(worksheetDTO2);
        worksheetDTO1.setId(null);
        assertThat(worksheetDTO1).isNotEqualTo(worksheetDTO2);
    }
}
