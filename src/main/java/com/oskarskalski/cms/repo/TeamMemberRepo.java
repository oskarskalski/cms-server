package com.oskarskalski.cms.repo;

import com.oskarskalski.cms.model.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamMemberRepo extends JpaRepository<TeamMember, Long> {
    Optional<TeamMember> findTeamMemberByTeamIdAndRoleId(String teamId, long roleId);
    void deleteByTeamIdAndUserId(String teamId, long userId);
}
