package com.oskarskalski.cms.content.member.repo;

import com.oskarskalski.cms.content.member.model.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamMemberRepo extends JpaRepository<TeamMember, Long> {
    Optional<TeamMember> findTeamMemberByTeamIdAndRoleId(String teamId, long roleId);
    Optional<List<TeamMember>> findAllByUserId(long id);
    void deleteByTeamIdAndUserId(String teamId, long userId);
}
