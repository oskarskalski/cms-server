package com.oskarskalski.cms.repo;

import com.oskarskalski.cms.dto.TeamMemberDto;
import com.oskarskalski.cms.model.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface TeamMemberRepo extends JpaRepository<TeamMember, Long> {
    Optional<TeamMember> findTeamMemberByTeamIdAndRoleId(String teamId, long roleId);
    Optional<List<TeamMember>> findAllByUserId(long id);
    void deleteByTeamIdAndUserId(String teamId, long userId);
}
