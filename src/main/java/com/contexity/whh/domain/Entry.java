package com.contexity.whh.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
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
    private LocalDate date;

    @Column(name = "comment")
    private String comment;

    @ManyToOne
    @JsonIgnoreProperties(value = { "user", "entries" }, allowSetters = true)
    private Worksheet worksheet;

    @ManyToOne
    @JsonIgnoreProperties(value = { "entries", "customer" }, allowSetters = true)
    private Project project;

    @ManyToOne
    @JsonIgnoreProperties(value = { "entries" }, allowSetters = true)
    private EntryType entryType;

    @ManyToOne
    @JsonIgnoreProperties(value = { "entries" }, allowSetters = true)
    private Worklocation worklocation;

    @ManyToMany(mappedBy = "entries")
    @JsonIgnoreProperties(value = { "entries" }, allowSetters = true)
    private Set<Tags> tags = new HashSet<>();

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

    public LocalDate getDate() {
        return this.date;
    }

    public Entry date(LocalDate date) {
        this.setDate(date);
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getComment() {
        return this.comment;
    }

    public Entry comment(String comment) {
        this.setComment(comment);
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public Worklocation getWorklocation() {
        return this.worklocation;
    }

    public void setWorklocation(Worklocation worklocation) {
        this.worklocation = worklocation;
    }

    public Entry worklocation(Worklocation worklocation) {
        this.setWorklocation(worklocation);
        return this;
    }

    public Set<Tags> getTags() {
        return this.tags;
    }

    public void setTags(Set<Tags> tags) {
        if (this.tags != null) {
            this.tags.forEach(i -> i.removeEntry(this));
        }
        if (tags != null) {
            tags.forEach(i -> i.addEntry(this));
        }
        this.tags = tags;
    }

    public Entry tags(Set<Tags> tags) {
        this.setTags(tags);
        return this;
    }

    public Entry addTags(Tags tags) {
        this.tags.add(tags);
        tags.getEntries().add(this);
        return this;
    }

    public Entry removeTags(Tags tags) {
        this.tags.remove(tags);
        tags.getEntries().remove(this);
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
            ", comment='" + getComment() + "'" +
            "}";
    }
}
