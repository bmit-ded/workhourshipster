package com.contexity.whh.repository;

import com.contexity.whh.domain.Tags;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.hibernate.annotations.QueryHints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class TagsRepositoryWithBagRelationshipsImpl implements TagsRepositoryWithBagRelationships {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Optional<Tags> fetchBagRelationships(Optional<Tags> tags) {
        return tags.map(this::fetchEntries);
    }

    @Override
    public Page<Tags> fetchBagRelationships(Page<Tags> tags) {
        return new PageImpl<>(fetchBagRelationships(tags.getContent()), tags.getPageable(), tags.getTotalElements());
    }

    @Override
    public List<Tags> fetchBagRelationships(List<Tags> tags) {
        return Optional.of(tags).map(this::fetchEntries).get();
    }

    Tags fetchEntries(Tags result) {
        return entityManager
            .createQuery("select tags from Tags tags left join fetch tags.entries where tags is :tags", Tags.class)
            .setParameter("tags", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Tags> fetchEntries(List<Tags> tags) {
        return entityManager
            .createQuery("select distinct tags from Tags tags left join fetch tags.entries where tags in :tags", Tags.class)
            .setParameter("tags", tags)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
    }
}
