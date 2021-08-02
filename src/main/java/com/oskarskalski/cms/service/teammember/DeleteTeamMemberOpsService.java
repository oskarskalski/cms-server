package com.oskarskalski.cms.service.teammember;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.oskarskalski.cms.crud.operation.SecuredDelete;
import com.oskarskalski.cms.exception.AccessDeniedException;
import com.oskarskalski.cms.exception.NotFoundException;
import com.oskarskalski.cms.configuration.JwtConfiguration;
import com.oskarskalski.cms.model.TeamMember;
import com.oskarskalski.cms.repo.TeamMemberRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteTeamMemberOpsService implements SecuredDelete<String> {
    private final TeamMemberRepo teamMemberRepo;
    private final JwtConfiguration jwtConfiguration = new JwtConfiguration();

    @Autowired
    public DeleteTeamMemberOpsService(TeamMemberRepo teamMemberRepo) {
        this.teamMemberRepo = teamMemberRepo;
    }

    public void softDeleteByIdAndAuthorizationHeader(String s, String header) {

    }

    public void hardDeleteByIdAndAuthorizationHeader(String teamId, String header) {
        DecodedJWT decodedJWT = jwtConfiguration.parse(header);
        long userId = Long.parseLong(decodedJWT.getClaim("id").asString());

        teamMemberRepo.deleteByTeamIdAndUserId(teamId, userId);
    }

    public void deleteTeamMemberByTeamIdAndAuthorizationHeaderAndUserId(
            String teamId, String header, long userId) {

        DecodedJWT decodedJWT = jwtConfiguration.parse(header);
        long teamCreatorId = Long.parseLong(decodedJWT.getClaim("id").asString());

        TeamMember teamMember = teamMemberRepo.findTeamMemberByTeamIdAndRoleId(teamId, 1)
                .orElseThrow(NotFoundException::new);

        if (teamMember.getUserId() == teamCreatorId){
            teamMemberRepo.deleteByTeamIdAndUserId(teamId, userId);
        }else{
            throw new AccessDeniedException();
        }
    }
}
