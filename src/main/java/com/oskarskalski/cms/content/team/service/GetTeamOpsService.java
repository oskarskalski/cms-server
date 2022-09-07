package com.oskarskalski.cms.content.team.service;

import com.oskarskalski.cms.content.team.model.dto.TeamDto;
import com.oskarskalski.cms.global.exception.AccessDeniedException;
import com.oskarskalski.cms.global.exception.NotFoundException;
import com.oskarskalski.cms.features.TeamCreator;
import com.oskarskalski.cms.content.team.model.Team;
import com.oskarskalski.cms.content.team.repo.TeamRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetTeamOpsService {
    private final TeamRepo teamRepo;

    @Autowired
    public GetTeamOpsService(TeamRepo teamRepo) {
        this.teamRepo = teamRepo;
    }

    public TeamDto getById(String id) {
        Team team = teamRepo.findById(id)
                .orElseThrow(NotFoundException::new);

        TeamDto teamDto = new TeamDto();
        teamDto.setId(team.getId());
        teamDto.setName(team.getName());
        teamDto.setDescription(team.getDescription());
        teamDto.setTeamMembers(team.getTeamMembers());
        return teamDto;
    }

    public String getTeamCode(String id, String header){
        Team team = teamRepo.findById(id)
                .orElseThrow(NotFoundException::new);
        TeamCreator teamCreator = new TeamCreator();
        if(teamCreator.getTeamCreator(team, header) != null){
            String teamCode = team.getCode();
            return teamCode;
        }else{
            throw new AccessDeniedException();
        }

    }

    public boolean isCorrectTeamCode(String id, String code){
        Team team = teamRepo.findByIdAndCode(id, code)
                .orElseThrow(NotFoundException::new);

        return team != null;
    }
}
