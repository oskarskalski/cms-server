package com.oskarskalski.cms.service;

import com.oskarskalski.cms.model.TeamMember;
import com.oskarskalski.cms.repo.TeamMemberRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamMemberService {
    private final TeamMemberRepo teamMemberRepo;

    @Autowired
    public TeamMemberService(TeamMemberRepo teamMemberRepo) {
        this.teamMemberRepo = teamMemberRepo;
    }

    public void add(TeamMember teamMember){
        teamMemberRepo.save(teamMember);
    }
}
