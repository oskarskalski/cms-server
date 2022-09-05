package com.oskarskalski.cms.content.team.repo;

import com.oskarskalski.cms.content.team.model.TeamRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRoleRepo extends JpaRepository<TeamRole, Long> {
}
