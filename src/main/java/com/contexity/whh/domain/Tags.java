package com.contexity.whh.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Tags.
 */
@Entity
@Table(name = "tags")
public class Tags implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(name = "rel_tags__entry", joinColumns = @JoinColumn(name = "tags_id"), inverseJoinColumns = @JoinColumn(name = "entry_id"))
    @JsonIgnoreProperties(value = { "worksheet", "project", "entryType", "worklocation", "tags" }, allowSetters = true)
    private Set<Entry> entries = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Tags id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Tags name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Entry> getEntries() {
        return this.entries;
    }

    public void setEntries(Set<Entry> entries) {
        this.entries = entries;
    }

    public Tags entries(Set<Entry> entries) {
        this.setEntries(entries);
        return this;
    }

    public Tags addEntry(Entry entry) {
        this.entries.add(entry);
        entry.getTags().add(this);
        return this;
    }

    public Tags removeEntry(Entry entry) {
        this.entries.remove(entry);
        entry.getTags().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tags)) {
            return false;
        }
        return id != null && id.equals(((Tags) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tags{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
