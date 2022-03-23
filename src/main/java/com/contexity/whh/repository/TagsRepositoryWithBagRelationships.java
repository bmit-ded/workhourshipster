package com.contexity.whh.repository;

import com.contexity.whh.domain.Tags;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface TagsRepositoryWithBagRelationships {
    Optional<Tags> fetchBagRelationships(Optional<Tags> tags);

    List<Tags> fetchBagRelationships(List<Tags> tags);

    Page<Tags> fetchBagRelationships(Page<Tags> tags);
}
