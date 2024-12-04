package org.cst8319.niyitangajeanpierre.talentgearbackend.repository;

import org.cst8319.niyitangajeanpierre.talentgearbackend.entity.ResumeEntity;
import org.cst8319.niyitangajeanpierre.talentgearbackend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResumeRepository extends JpaRepository<ResumeEntity, Long> {

    Optional<ResumeEntity> findByUser(UserEntity user);
}
