package com.contexity.whh.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.contexity.whh.domain.EntryType} entity.
 */
public class EntryTypeDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Boolean worktime;

    private Boolean billable;

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

    public Boolean getWorktime() {
        return worktime;
    }

    public void setWorktime(Boolean worktime) {
        this.worktime = worktime;
    }

    public Boolean getBillable() {
        return billable;
    }

    public void setBillable(Boolean billable) {
        this.billable = billable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EntryTypeDTO)) {
            return false;
        }

        EntryTypeDTO entryTypeDTO = (EntryTypeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, entryTypeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EntryTypeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", worktime='" + getWorktime() + "'" +
            ", billable='" + getBillable() + "'" +
            "}";
    }
}
