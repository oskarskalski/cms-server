package com.oskarskalski.cms.features;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.oskarskalski.cms.global.exception.AccessDeniedException;
import com.oskarskalski.cms.global.configuration.JwtConfiguration;
import com.oskarskalski.cms.content.team.model.Team;
import com.oskarskalski.cms.content.member.model.TeamMember;

public class TeamCreator {
    private final JwtConfiguration jwtConfiguration = new JwtConfiguration();

    public TeamMember getTeamCreator(Team team, String header){
        DecodedJWT decodedJWT = jwtConfiguration.parse(header);
        long userId = Long.parseLong(decodedJWT.getClaim("id").asString());

        for(TeamMember teamMember: team.getTeamMembers())
            if(teamMember.getRoleId() == 1)
                if(teamMember.getUserId() == userId)
                    return teamMember;
                else
                    throw new AccessDeniedException();
        throw new AccessDeniedException();
    }
}
