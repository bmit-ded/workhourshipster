package com.contexity.whh.repository;

import com.contexity.whh.domain.Customer;
import com.contexity.whh.domain.Project;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Customer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("select project from Project project where customer.id =:id")
    List<Project> getProjects(@Param("id") Long id);
}
