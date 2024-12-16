package org.cst8319.niyitangajeanpierre.talentgearbackend.repository;

import org.cst8319.niyitangajeanpierre.talentgearbackend.entity.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<JobEntity, Long> {
    @Query("SELECT j FROM JobEntity j WHERE j.id = :jobId AND j.employer.username = :username")
    Optional<JobEntity> findByIdAndEmployerUsername(@Param("jobId") Long jobId, @Param("username") String username);
}
