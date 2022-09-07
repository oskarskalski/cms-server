package com.oskarskalski.cms.content.member.service;

import com.oskarskalski.cms.content.team.model.dto.CodeDto;
import com.oskarskalski.cms.global.configuration.JwtConfiguration;
import com.oskarskalski.cms.content.member.model.TeamMember;
import com.oskarskalski.cms.content.member.repo.TeamMemberRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AddTeamMemberOpsService{
    private final TeamMemberRepo teamMemberRepo;
    private final JwtConfiguration jwtConfiguration = new JwtConfiguration();

    public AddTeamMemberOpsService(TeamMemberRepo teamMemberRepo) {
        this.teamMemberRepo = teamMemberRepo;
    }

    public void addByObject(TeamMember teamMember) {
        teamMemberRepo.save(teamMember);
    }

    public void addByObjectAndAuthorizationHeader(CodeDto codeDto, String header) {
        long userId = Long.parseLong(jwtConfiguration.parse(header).getClaim("id").asString());

        if (userId > 0) {
            RestTemplate restTemplate = new RestTemplate();
            String checkTeamCode
                    = "http://localhost:8080/api/team/code/check/" + codeDto.getTeamId() + "?code=" + codeDto.getCode();
            ResponseEntity<Boolean> response
                    = restTemplate.getForEntity(checkTeamCode, Boolean.class);

            if (response.getBody()) {
                TeamMember teamMember = new TeamMember();
                teamMember.setUserId(userId);
                teamMember.setTeamId(codeDto.getTeamId());
                teamMember.setRoleId(2);


            }
        }
    }
}
