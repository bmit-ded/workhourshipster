package com.contexity.whh.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.contexity.whh.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WorklocationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Worklocation.class);
        Worklocation worklocation1 = new Worklocation();
        worklocation1.setId(1L);
        Worklocation worklocation2 = new Worklocation();
        worklocation2.setId(worklocation1.getId());
        assertThat(worklocation1).isEqualTo(worklocation2);
        worklocation2.setId(2L);
        assertThat(worklocation1).isNotEqualTo(worklocation2);
        worklocation1.setId(null);
        assertThat(worklocation1).isNotEqualTo(worklocation2);
    }
}
