package com.contexity.whh.repository;

import com.contexity.whh.domain.Entry;
import com.contexity.whh.service.dto.EntryDTO;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Entry entity.
 */
@Repository
public interface EntryRepository extends JpaRepository<Entry, Long> {
    default Optional<Entry> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Entry> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Entry> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct entry from Entry entry left join fetch entry.project left join fetch entry.entryType left join fetch entry.worklocation",
        countQuery = "select count(distinct entry) from Entry entry"
    )
    Page<Entry> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct entry from Entry entry left join fetch entry.project left join fetch entry.entryType left join fetch entry.worklocation"
    )
    List<Entry> findAllWithToOneRelationships();

    @Query(
        "select entry from Entry entry left join fetch entry.project left join fetch entry.entryType left join fetch entry.worklocation where entry.id =:id"
    )
    Optional<Entry> findOneWithToOneRelationships(@Param("id") Long id);

    //myWork

    @Query("select entry from Entry entry where project.id = :id")
    List<Entry> findbyProject(@Param("id") Long id);

    @Query("select entry from Entry entry where project.id = :id and MONTH(entry.date) = :month and YEAR(entry.date) = :year")
    List<Entry> findbyProjectandDate(@Param("id") Long id, @Param("month") int month, @Param("year") int year);

    @Query("select entry from Entry entry where project.id = :id and entry.date between :date1 and :date2")
    List<Entry> findbyProjectandDateWeek(@Param("id") Long id, @Param("date1") LocalDate date1, @Param("date2") LocalDate date2);

    @Query("select entry from Entry entry where worksheet.id = :id")
    List<Entry> findbyWorksheet(@Param("id") Long id);

    @Query("select entry from Entry entry where worksheet.id = :id and MONTH(entry.date) = :month and YEAR(entry.date) = :year")
    List<Entry> findbyWorksheetandDate(@Param("id") Long id, @Param("month") int month, @Param("year") int year);

    @Query("select entry from Entry entry where worksheet.id = :id and entry.date between :date1 and :date2")
    List<Entry> findbyWorksheetandDateWeek(@Param("id") Long id, @Param("date1") LocalDate date1, @Param("date2") LocalDate date2);
}
