package com.oskarskalski.cms.service;

import com.oskarskalski.cms.dto.TeamDto;
import com.oskarskalski.cms.exception.NotFoundException;
import com.oskarskalski.cms.model.Team;
import com.oskarskalski.cms.model.TeamMember;
import com.oskarskalski.cms.repo.TeamRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.UUID;

@Service
public class TeamService {
    private final TeamRepo teamRepo;

    @Autowired
    public TeamService(TeamRepo teamRepo) {
        this.teamRepo = teamRepo;
    }

    public void createTeam(Team team, String header) {
        String generateId = UUID.randomUUID().toString();
        team.setId(generateId);

        addUserToTeam(generateId, header, 1);
        teamRepo.save(team);
        generateCodeForJoiningTeam(generateId, header);
    }

    public TeamDto getTeamById(String id) {
        Team team = teamRepo.findById(id)
                .orElseThrow(NotFoundException::new);

        TeamDto teamDto = new TeamDto();
        teamDto.setName(team.getName());
        teamDto.setDescription(team.getDescription());
        return teamDto;
    }

    public void generateCodeForJoiningTeam(String id, String header) {
        Team team = teamRepo.findById(id)
                .orElseThrow(NotFoundException::new);

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < 6; i++){
            char generateCharacter = (char) (Math.random() * ('z' - 'a') + 'a');
            stringBuilder.append(generateCharacter);
        }

        team.setCode(stringBuilder.toString());
    }

    public void joinTeamByIdAndSecretCode(String id, String code, String header) {
        Team team = teamRepo.findById(id)
                .orElseThrow(NotFoundException::new);

        if (team.getCode().equals(code)) {
            addUserToTeam(id, header, 2);
        }
    }

    public int addUserToTeam(String id, String header, long role) {
        RestTemplate restTemplate = new RestTemplate();
        TeamMember teamMember = new TeamMember();
        teamMember.setRoleId(role);
        teamMember.setTeamId(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", header);

        HttpEntity<TeamMember> request =
                new HttpEntity<>(teamMember, headers);

        ResponseEntity<String> responseEntityStr = restTemplate.
                postForEntity("http://localhost:8080/api/teamMember/add", request, String.class);

        return responseEntityStr.getStatusCode().value();
    }
}
