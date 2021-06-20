package com.oskarskalski.cms.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oskarskalski.cms.dto.TeamDto;
import com.oskarskalski.cms.exception.AccessDeniedException;
import com.oskarskalski.cms.exception.InvalidDataException;
import com.oskarskalski.cms.exception.NotFoundException;
import com.oskarskalski.cms.json.JwtConfiguration;
import com.oskarskalski.cms.model.Team;
import com.oskarskalski.cms.model.TeamMember;
import com.oskarskalski.cms.repo.TeamRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.UUID;

@Service
public class TeamService {
    private final TeamRepo teamRepo;
    private final JwtConfiguration jwtConfiguration = new JwtConfiguration();

    @Autowired
    public TeamService(TeamRepo teamRepo) {
        this.teamRepo = teamRepo;
    }

    public void createTeam(TeamDto teamDto, String header) {
        if (teamDto.getName().length() < 5 || teamDto.getName().length() > 35) {
            throw new InvalidDataException();
        }

        if (teamDto.getDescription().length() < 10 || teamDto.getDescription().length() > 100) {
            throw new InvalidDataException();
        }

        DecodedJWT decodedJWT = jwtConfiguration.parse(header);
        long user_id = Long.parseLong(decodedJWT.getClaim("id").asString());
        if (user_id > 0) {
            String generateId = UUID.randomUUID().toString();
            Team team = new Team();
            team.setName(teamDto.getName());
            team.setDescription(teamDto.getDescription());
            team.setId(generateId);
            team.setCode(generateCodeForJoiningTeam());

            teamRepo.save(team);
            addUserToTeam(generateId, header, 1, user_id);
        } else
            throw new AccessDeniedException();
    }

    public TeamDto getTeamById(String id) {
        Team team = teamRepo.findById(id)
                .orElseThrow(NotFoundException::new);

        TeamDto teamDto = new TeamDto();
        teamDto.setName(team.getName());
        teamDto.setDescription(team.getDescription());
        teamDto.setTeamMembers(team.getTeamMembers());
        return teamDto;
    }

    public void joinTeamByIdAndSecretCode(String team_id, String code, String header) {
        Team team = teamRepo.findById(team_id)
                .orElseThrow(NotFoundException::new);

        long userId = Long.parseLong(jwtConfiguration.parse(header).getClaim("id").asString());

        if (team.getCode().equals(code)) {
            addUserToTeam(team_id, header, 2, userId);
        }
    }

    public void updateTeam(String id, TeamDto teamDto, String header) {
        if (!header.startsWith("Bearer "))
            throw new AccessDeniedException();

        if (teamDto.getDescription() == null && teamDto.getName() == null)
            throw new InvalidDataException();

        Team team = teamRepo.findById(id)
                .orElseThrow(NotFoundException::new);

        long userId = Long.parseLong(jwtConfiguration.parse(header).getClaim("id").asString());
        long creatorId = getCreatorIdOfTheTeam(id, header);

        if (userId == creatorId) {
            boolean updated = false;
            if (teamDto.getName() != null &&
                    teamDto.getName().length() >= 5 && teamDto.getName().length() <= 35) {
                team.setName(teamDto.getName());
                updated = true;
            }

            if (teamDto.getDescription() != null
                    && teamDto.getDescription().length() >= 10 && teamDto.getDescription().length() <= 100) {
                team.setDescription(teamDto.getDescription());
                updated = true;
            }

            if (updated)
                teamRepo.save(team);
            else
                throw new InvalidDataException();
        } else
            throw new AccessDeniedException();
    }

    public String generateCodeForJoiningTeam() {
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            char generateCharacter = (char) (Math.random() * ('z' - 'a') + 'a');
            code.append(generateCharacter);
        }

        return code.toString();
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
        ResponseEntity<String> response =
                restTemplate.
                        postForEntity("http://localhost:8080/api/teamMember/add", request, String.class);
    }

    public void updateTeamCode(String id, String header) {
        Team team = teamRepo.findById(id)
                .orElseThrow(NotFoundException::new);

        long userId = Long.parseLong(jwtConfiguration.parse(header).getClaim("id").asString());
        long creatorId = getCreatorIdOfTheTeam(id, header);

        if (creatorId == userId) {
            team.setCode(generateCodeForJoiningTeam());
            teamRepo.save(team);
        } else
            throw new AccessDeniedException();
    }

    public long getCreatorIdOfTheTeam(String teamId, String header) {

        RestTemplate restTemplate = new RestTemplate();
        String getCreatorId
                = "http://localhost:8080/api/teamMember/creator/" + teamId;
        ResponseEntity<String> response
                = restTemplate.getForEntity(getCreatorId, String.class);

        long creatorId = Long.parseLong(response.getBody());
        return creatorId;
    }
}
