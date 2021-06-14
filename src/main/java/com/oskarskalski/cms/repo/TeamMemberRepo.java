package com.oskarskalski.cms.repo;

import com.oskarskalski.cms.model.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamMemberRepo extends JpaRepository<TeamMember, Long> {
}
