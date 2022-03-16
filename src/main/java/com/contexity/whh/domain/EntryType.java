package com.contexity.whh.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A EntryType.
 */
@Entity
@Table(name = "entry_type")
public class EntryType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "worktime", nullable = false)
    private Boolean worktime;

    @OneToMany(mappedBy = "entryType")
    @JsonIgnoreProperties(value = { "worksheet", "project", "entryType" }, allowSetters = true)
    private Set<Entry> entries = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EntryType id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public EntryType name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getWorktime() {
        return this.worktime;
    }

    public EntryType worktime(Boolean worktime) {
        this.setWorktime(worktime);
        return this;
    }

    public void setWorktime(Boolean worktime) {
        this.worktime = worktime;
    }

    public Set<Entry> getEntries() {
        return this.entries;
    }

    public void setEntries(Set<Entry> entries) {
        if (this.entries != null) {
            this.entries.forEach(i -> i.setEntryType(null));
        }
        if (entries != null) {
            entries.forEach(i -> i.setEntryType(this));
        }
        this.entries = entries;
    }

    public EntryType entries(Set<Entry> entries) {
        this.setEntries(entries);
        return this;
    }

    public EntryType addEntry(Entry entry) {
        this.entries.add(entry);
        entry.setEntryType(this);
        return this;
    }

    public EntryType removeEntry(Entry entry) {
        this.entries.remove(entry);
        entry.setEntryType(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EntryType)) {
            return false;
        }
        return id != null && id.equals(((EntryType) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EntryType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", worktime='" + getWorktime() + "'" +
            "}";
    }
}
