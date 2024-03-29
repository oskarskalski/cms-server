package com.oskarskalski.cms.content.team.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.oskarskalski.cms.content.team.model.dto.TeamDto;
import com.oskarskalski.cms.global.exception.AccessDeniedException;
import com.oskarskalski.cms.global.exception.InvalidDataException;
import com.oskarskalski.cms.features.CodeGenerator;
import com.oskarskalski.cms.global.configuration.JwtConfiguration;
import com.oskarskalski.cms.content.team.model.Team;
import com.oskarskalski.cms.content.member.model.TeamMember;
import com.oskarskalski.cms.content.team.repo.TeamRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.UUID;

@Service
public class AddTeamOpsService {
    private final TeamRepo teamRepo;
    private final JwtConfiguration jwtConfiguration = new JwtConfiguration();

    @Autowired
    public AddTeamOpsService(TeamRepo teamRepo) {
        this.teamRepo = teamRepo;
    }

    public void addByObjectAndAuthorizationHeader(TeamDto teamDto, String header) {
        if (teamDto.getName() == null ||
                teamDto.getName().length() < 5 || teamDto.getName().length() > 35) {
            throw new InvalidDataException();
        }

        if (teamDto.getDescription() == null ||
                teamDto.getDescription().length() < 10 || teamDto.getDescription().length() > 100) {
            throw new InvalidDataException();
        }

        DecodedJWT decodedJWT = jwtConfiguration.parse(header);
        long userId = Long.parseLong(decodedJWT.getClaim("id").asString());
        if (userId > 0) {
            String generateId = UUID.randomUUID().toString();
            Team team = new Team();
            team.setName(teamDto.getName());
            team.setDescription(teamDto.getDescription());
            team.setId(generateId);
            team.setCode(CodeGenerator.generate(6));

            teamRepo.save(team);
            addUserToTeam(generateId, header, 1, userId);
        } else
            throw new AccessDeniedException();
    }

    public void addUserToTeam(String id, String header, long role, long userId) {
        RestTemplate restTemplate = new RestTemplate();
        TeamMember teamMember = new TeamMember();
        teamMember.setRoleId(role);
        teamMember.setTeamId(id);
        teamMember.setUserId(userId);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", header);

        HttpEntity<TeamMember> request =
                new HttpEntity<>(teamMember, headers);

        String currentSiteName = System.getenv("SITE_URL");
        ResponseEntity<String> response =
                restTemplate.
                        postForEntity(currentSiteName + "/api/teamMember/add", request, String.class);
    }
}
