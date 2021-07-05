package com.oskarskalski.cms.service.team;

import com.oskarskalski.cms.crud.operation.Get;
import com.oskarskalski.cms.dto.TeamDto;
import com.oskarskalski.cms.exception.AccessDeniedException;
import com.oskarskalski.cms.exception.NotFoundException;
import com.oskarskalski.cms.features.TeamCreator;
import com.oskarskalski.cms.json.JwtConfiguration;
import com.oskarskalski.cms.model.Team;
import com.oskarskalski.cms.repo.TeamRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GetTeamService implements Get<TeamDto, String> {
    private final TeamRepo teamRepo;

    @Autowired
    public GetTeamService(TeamRepo teamRepo) {
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
