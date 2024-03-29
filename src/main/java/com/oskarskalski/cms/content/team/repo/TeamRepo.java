package com.oskarskalski.cms.content.team.repo;

import com.oskarskalski.cms.content.team.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamRepo extends JpaRepository<Team, String> {
    Optional<Team> findByIdAndCode(String id, String code);
}
