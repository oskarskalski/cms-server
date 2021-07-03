package com.oskarskalski.cms.service.team;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.oskarskalski.cms.dto.TeamDto;
import com.oskarskalski.cms.exception.AccessDeniedException;
import com.oskarskalski.cms.exception.InvalidDataException;
import com.oskarskalski.cms.json.JwtConfiguration;
import com.oskarskalski.cms.model.Team;
import com.oskarskalski.cms.model.TeamMember;
import com.oskarskalski.cms.repo.TeamRepo;
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
public class AddTeamService {
    private final TeamRepo teamRepo;
    private final JwtConfiguration jwtConfiguration = new JwtConfiguration();

    @Autowired
    public AddTeamService(TeamRepo teamRepo) {
        this.teamRepo = teamRepo;
    }

    public void add(TeamDto teamDto, String header) {
        if (teamDto.getName() == null ||
                teamDto.getName().length() < 5 || teamDto.getName().length() > 35) {
            throw new InvalidDataException();
        }

        if (teamDto.getDescription() == null ||
                teamDto.getDescription().length() < 10 || teamDto.getDescription().length() > 100) {
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
}
