package org.cst8319.niyitangajeanpierre.talentgearbackend.repository;

import org.cst8319.niyitangajeanpierre.talentgearbackend.entity.JobApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplicationEntity, Long> {
    List<JobApplicationEntity> findByJobId(Long jobId);
}
