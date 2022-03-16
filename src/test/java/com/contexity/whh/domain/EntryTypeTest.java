package com.contexity.whh.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.contexity.whh.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EntryTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EntryType.class);
        EntryType entryType1 = new EntryType();
        entryType1.setId(1L);
        EntryType entryType2 = new EntryType();
        entryType2.setId(entryType1.getId());
        assertThat(entryType1).isEqualTo(entryType2);
        entryType2.setId(2L);
        assertThat(entryType1).isNotEqualTo(entryType2);
        entryType1.setId(null);
        assertThat(entryType1).isNotEqualTo(entryType2);
    }
}
