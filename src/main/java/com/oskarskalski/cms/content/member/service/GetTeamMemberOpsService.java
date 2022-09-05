package com.oskarskalski.cms.content.member.service;

import com.oskarskalski.cms.content.member.model.dto.TeamMemberDto;
import com.oskarskalski.cms.global.exception.NotFoundException;
import com.oskarskalski.cms.content.member.model.TeamMember;
import com.oskarskalski.cms.content.member.repo.TeamMemberRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetTeamMemberOpsService {
    private final TeamMemberRepo teamMemberRepo;

    @Autowired
    public GetTeamMemberOpsService(TeamMemberRepo teamMemberRepo) {
        this.teamMemberRepo = teamMemberRepo;
    }

    public List<TeamMemberDto> getTeamsByUserId(long id){
        List<TeamMember> teamMemberList = teamMemberRepo.findAllByUserId(id)
                .orElseThrow(NotFoundException::new);

        return mapTeamMemberToDto(teamMemberList);
    }

    private List<TeamMemberDto> mapTeamMemberToDto(List<TeamMember> teamMemberList){
        List<TeamMemberDto> teamMemberDtoList = new ArrayList<>();

        for(TeamMember teamMember: teamMemberList){
            TeamMemberDto teamMemberDto = new TeamMemberDto();
            teamMemberDto.setTeamId(teamMember.getTeamId());
            teamMemberDto.setUserId(teamMember.getUserId());

            teamMemberDtoList.add(teamMemberDto);
        }

        return teamMemberDtoList;
    }
}
