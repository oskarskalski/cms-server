package com.oskarskalski.cms.service;

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

    public void createTeam(Team team){
        String generateId = UUID.randomUUID().toString();
        team.setId(generateId);

        RestTemplate restTemplate = new RestTemplate();
        TeamMember teamMember = new TeamMember();
        teamMember.setRoleId(1L);
        teamMember.setTeamId(generateId);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer kekm");

        HttpEntity<TeamMember> request =
                new HttpEntity<TeamMember>(teamMember, headers);

        ResponseEntity<String> responseEntityStr = restTemplate.
                postForEntity("http://localhost:8080/api/teamMember/add", request, String.class);

        System.out.println(responseEntityStr.getStatusCode());

        teamRepo.save(team);
    }
}
