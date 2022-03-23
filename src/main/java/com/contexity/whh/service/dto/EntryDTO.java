package com.contexity.whh.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
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
    private LocalDate date;

    private String comment;

    private WorksheetDTO worksheet;

    private ProjectDTO project;

    private EntryTypeDTO entryType;

    private WorklocationDTO worklocation;

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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public WorklocationDTO getWorklocation() {
        return worklocation;
    }

    public void setWorklocation(WorklocationDTO worklocation) {
        this.worklocation = worklocation;
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
            ", comment='" + getComment() + "'" +
            ", worksheet=" + getWorksheet() +
            ", project=" + getProject() +
            ", entryType=" + getEntryType() +
            ", worklocation=" + getWorklocation() +
            "}";
    }
}
