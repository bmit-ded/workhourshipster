package com.contexity.whh.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Worksheet.
 */
@Entity
@Table(name = "worksheet")
public class Worksheet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(mappedBy = "worksheet")
    @JsonIgnoreProperties(value = { "worksheet", "project", "entryType" }, allowSetters = true)
    private Set<Entry> entries = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Worksheet id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Worksheet user(User user) {
        this.setUser(user);
        return this;
    }

    public Set<Entry> getEntries() {
        return this.entries;
    }

    public void setEntries(Set<Entry> entries) {
        if (this.entries != null) {
            this.entries.forEach(i -> i.setWorksheet(null));
        }
        if (entries != null) {
            entries.forEach(i -> i.setWorksheet(this));
        }
        this.entries = entries;
    }

    public Worksheet entries(Set<Entry> entries) {
        this.setEntries(entries);
        return this;
    }

    public Worksheet addEntry(Entry entry) {
        this.entries.add(entry);
        entry.setWorksheet(this);
        return this;
    }

    public Worksheet removeEntry(Entry entry) {
        this.entries.remove(entry);
        entry.setWorksheet(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Worksheet)) {
            return false;
        }
        return id != null && id.equals(((Worksheet) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Worksheet{" +
            "id=" + getId() +
            "}";
    }
}
