package org.cst8319.niyitangajeanpierre.talentgearbackend.repository;

import org.cst8319.niyitangajeanpierre.talentgearbackend.entity.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<JobEntity, Long> {
    // Find job by ID and employer who posted it
    @Query("SELECT j FROM JobEntity j WHERE j.id = :jobId AND j.employer.username = :username")
    Optional<JobEntity> findByIdAndEmployerUsername(@Param("jobId") Long jobId, @Param("username") String username);

    @Query("SELECT j FROM JobEntity j WHERE LOWER(j.industry) LIKE LOWER(CONCAT('%', :industry, '%'))")
    List<JobEntity> findByIndustryContainingIgnoreCase(String industry);

    @Query("SELECT j FROM JobEntity j WHERE LOWER(j.location) LIKE LOWER(CONCAT('%', :location, '%'))")
    List<JobEntity> findByLocationContainingIgnoreCase(String location);

    @Query("SELECT j FROM JobEntity j WHERE j.salary >= :minSalary")
    List<JobEntity> findBySalaryGreaterThanEqual(Double minSalary);

    @Query("SELECT j FROM JobEntity j WHERE j.salary <= :maxSalary")
    List<JobEntity> findBySalaryLessThanEqual(Double maxSalary);
}
