package com.contexity.whh.repository;

import com.contexity.whh.domain.Worksheet;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Worksheet entity.
 */
@Repository
public interface WorksheetRepository extends JpaRepository<Worksheet, Long> {
    default Optional<Worksheet> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Worksheet> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Worksheet> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct worksheet from Worksheet worksheet left join fetch worksheet.user",
        countQuery = "select count(distinct worksheet) from Worksheet worksheet"
    )
    Page<Worksheet> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct worksheet from Worksheet worksheet left join fetch worksheet.user")
    List<Worksheet> findAllWithToOneRelationships();

    @Query("select worksheet from Worksheet worksheet left join fetch worksheet.user where worksheet.id =:id")
    Optional<Worksheet> findOneWithToOneRelationships(@Param("id") Long id);
}
