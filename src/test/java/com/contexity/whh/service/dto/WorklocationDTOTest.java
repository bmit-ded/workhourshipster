package com.contexity.whh.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.contexity.whh.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WorklocationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorklocationDTO.class);
        WorklocationDTO worklocationDTO1 = new WorklocationDTO();
        worklocationDTO1.setId(1L);
        WorklocationDTO worklocationDTO2 = new WorklocationDTO();
        assertThat(worklocationDTO1).isNotEqualTo(worklocationDTO2);
        worklocationDTO2.setId(worklocationDTO1.getId());
        assertThat(worklocationDTO1).isEqualTo(worklocationDTO2);
        worklocationDTO2.setId(2L);
        assertThat(worklocationDTO1).isNotEqualTo(worklocationDTO2);
        worklocationDTO1.setId(null);
        assertThat(worklocationDTO1).isNotEqualTo(worklocationDTO2);
    }
}
