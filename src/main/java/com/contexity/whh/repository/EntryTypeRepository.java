package com.contexity.whh.repository;

import com.contexity.whh.domain.EntryType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the EntryType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntryTypeRepository extends JpaRepository<EntryType, Long> {}
