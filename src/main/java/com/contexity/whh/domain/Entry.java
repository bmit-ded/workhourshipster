package com.contexity.whh.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Entry.
 */
@Entity
@Table(name = "entry")
public class Entry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "hours", nullable = false)
    private Double hours;

    @NotNull
    @Column(name = "date", nullable = false)
    private Instant date;

    @ManyToOne
    @JsonIgnoreProperties(value = { "user", "entries" }, allowSetters = true)
    private Worksheet worksheet;

    @ManyToOne
    @JsonIgnoreProperties(value = { "entries", "customer" }, allowSetters = true)
    private Project project;

    @ManyToOne
    @JsonIgnoreProperties(value = { "entries" }, allowSetters = true)
    private EntryType entryType;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Entry id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getHours() {
        return this.hours;
    }

    public Entry hours(Double hours) {
        this.setHours(hours);
        return this;
    }

    public void setHours(Double hours) {
        this.hours = hours;
    }

    public Instant getDate() {
        return this.date;
    }

    public Entry date(Instant date) {
        this.setDate(date);
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Worksheet getWorksheet() {
        return this.worksheet;
    }

    public void setWorksheet(Worksheet worksheet) {
        this.worksheet = worksheet;
    }

    public Entry worksheet(Worksheet worksheet) {
        this.setWorksheet(worksheet);
        return this;
    }

    public Project getProject() {
        return this.project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Entry project(Project project) {
        this.setProject(project);
        return this;
    }

    public EntryType getEntryType() {
        return this.entryType;
    }

    public void setEntryType(EntryType entryType) {
        this.entryType = entryType;
    }

    public Entry entryType(EntryType entryType) {
        this.setEntryType(entryType);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Entry)) {
            return false;
        }
        return id != null && id.equals(((Entry) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Entry{" +
            "id=" + getId() +
            ", hours=" + getHours() +
            ", date='" + getDate() + "'" +
            "}";
    }
}
