package com.contexity.whh.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.contexity.whh.domain.Tags} entity.
 */
public class TagsDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private Set<EntryDTO> entries = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<EntryDTO> getEntries() {
        return entries;
    }

    public void setEntries(Set<EntryDTO> entries) {
        this.entries = entries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TagsDTO)) {
            return false;
        }

        TagsDTO tagsDTO = (TagsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tagsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TagsDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", entries=" + getEntries() +
            "}";
    }
}
