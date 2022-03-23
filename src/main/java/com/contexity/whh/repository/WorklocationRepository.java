package com.contexity.whh.repository;

import com.contexity.whh.domain.Worklocation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Worklocation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorklocationRepository extends JpaRepository<Worklocation, Long> {}
