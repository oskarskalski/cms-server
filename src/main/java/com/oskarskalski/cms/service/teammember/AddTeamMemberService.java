package com.oskarskalski.cms.service.teammember;

import com.oskarskalski.cms.json.JwtConfiguration;
import com.oskarskalski.cms.model.TeamMember;
import com.oskarskalski.cms.repo.TeamMemberRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AddTeamMemberService {
    private final TeamMemberRepo teamMemberRepo;
    private final JwtConfiguration jwtConfiguration = new JwtConfiguration();

    @Autowired
    public AddTeamMemberService(TeamMemberRepo teamMemberRepo) {
        this.teamMemberRepo = teamMemberRepo;
    }

    public void addTeamMember(TeamMember teamMember) {
        teamMemberRepo.save(teamMember);
    }

    public void addTeamMemberByTeamIdAndCode(String teamId, String code, String header) {
        long userId = Long.parseLong(jwtConfiguration.parse(header).getClaim("id").asString());

        if (userId > 0) {
            RestTemplate restTemplate = new RestTemplate();
            String checkTeamCode
                    = "http://localhost:8080/api/team/code/check/" + teamId + "?code=" + code;
            ResponseEntity<Boolean> response
                    = restTemplate.getForEntity(checkTeamCode, Boolean.class);

            if (response.getBody()) {
                TeamMember teamMember = new TeamMember();
                teamMember.setUserId(userId);
                teamMember.setTeamId(teamId);
                teamMember.setRoleId(2);


            }
        }
    }
}
