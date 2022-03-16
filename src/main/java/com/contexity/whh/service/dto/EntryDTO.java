package com.contexity.whh.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.contexity.whh.domain.Entry} entity.
 */
public class EntryDTO implements Serializable {

    private Long id;

    @NotNull
    private Double hours;

    @NotNull
    private Instant date;

    private WorksheetDTO worksheet;

    private ProjectDTO project;

    private EntryTypeDTO entryType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getHours() {
        return hours;
    }

    public void setHours(Double hours) {
        this.hours = hours;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public WorksheetDTO getWorksheet() {
        return worksheet;
    }

    public void setWorksheet(WorksheetDTO worksheet) {
        this.worksheet = worksheet;
    }

    public ProjectDTO getProject() {
        return project;
    }

    public void setProject(ProjectDTO project) {
        this.project = project;
    }

    public EntryTypeDTO getEntryType() {
        return entryType;
    }

    public void setEntryType(EntryTypeDTO entryType) {
        this.entryType = entryType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EntryDTO)) {
            return false;
        }

        EntryDTO entryDTO = (EntryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, entryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EntryDTO{" +
            "id=" + getId() +
            ", hours=" + getHours() +
            ", date='" + getDate() + "'" +
            ", worksheet=" + getWorksheet() +
            ", project=" + getProject() +
            ", entryType=" + getEntryType() +
            "}";
    }
}
