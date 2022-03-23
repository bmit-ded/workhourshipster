package com.contexity.whh.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Worklocation.
 */
@Entity
@Table(name = "worklocation")
public class Worklocation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "worklocation")
    @JsonIgnoreProperties(value = { "worksheet", "project", "entryType", "worklocation", "tags" }, allowSetters = true)
    private Set<Entry> entries = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Worklocation id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Worklocation name(String name) {
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
        if (this.entries != null) {
            this.entries.forEach(i -> i.setWorklocation(null));
        }
        if (entries != null) {
            entries.forEach(i -> i.setWorklocation(this));
        }
        this.entries = entries;
    }

    public Worklocation entries(Set<Entry> entries) {
        this.setEntries(entries);
        return this;
    }

    public Worklocation addEntry(Entry entry) {
        this.entries.add(entry);
        entry.setWorklocation(this);
        return this;
    }

    public Worklocation removeEntry(Entry entry) {
        this.entries.remove(entry);
        entry.setWorklocation(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Worklocation)) {
            return false;
        }
        return id != null && id.equals(((Worklocation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Worklocation{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
