package com.oskarskalski.cms.repo;

import com.oskarskalski.cms.model.TeamRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRoleRepo extends JpaRepository<TeamRole, Long> {
}
