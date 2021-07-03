package com.oskarskalski.cms.service;

import com.oskarskalski.cms.exception.NotFoundException;
import com.oskarskalski.cms.json.JwtConfiguration;
import com.oskarskalski.cms.model.Team;
import com.oskarskalski.cms.model.TeamMember;
import com.oskarskalski.cms.repo.TeamMemberRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TeamMemberService {
    private final TeamMemberRepo teamMemberRepo;
    private final JwtConfiguration jwtConfiguration = new JwtConfiguration();

    @Autowired
    public TeamMemberService(TeamMemberRepo teamMemberRepo) {
        this.teamMemberRepo = teamMemberRepo;
    }

    public void add(TeamMember teamMember){
        teamMemberRepo.save(teamMember);
    }

    public long getTeamCreatorIdByTeamId(String id) {
        TeamMember teamMember = teamMemberRepo.findTeamMemberByTeamIdAndRoleId(id, 1)
                .orElseThrow(NotFoundException::new);

        return teamMember.getUserId();
    }

    public void joinTeamByIdAndSecretCode(String teamId, String code, String header) {
//        long userId = Long.parseLong(jwtConfiguration.parse(header).getClaim("id").asString());
//
//        RestTemplate restTemplate = new RestTemplate();
//        String fooResourceUrl
//                = "http://localhost:8080/spring-rest/foos";
//        ResponseEntity<String> response
//                = restTemplate.getForEntity(fooResourceUrl + "/1", String.class);
//
//        if (team.getCode().equals(code)) {
//            addUserToTeam(teamId, header, 2, userId);
//        }
    }
}
