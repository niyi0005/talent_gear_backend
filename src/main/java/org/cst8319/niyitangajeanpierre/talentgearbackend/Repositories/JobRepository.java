package org.cst8319.niyitangajeanpierre.talentgearbackend.Repositories;

import org.cst8319.niyitangajeanpierre.talentgearbackend.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {

    //Custom queries coming later

}
