package com.oskarskalski.cms.controller.teammember;

import com.oskarskalski.cms.dto.TeamMemberDto;
import com.oskarskalski.cms.service.teammember.GetTeamMemberOpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/teamMember/")
public class GetTeamMemberController {
    private final GetTeamMemberOpsService getTeamMemberOpsService;

    @Autowired
    public GetTeamMemberController(GetTeamMemberOpsService getTeamMemberOpsService) {
        this.getTeamMemberOpsService = getTeamMemberOpsService;
    }

    @GetMapping("{id}")
    public List<TeamMemberDto> getTeamsIdByUserId(@PathVariable long id){
        return getTeamMemberOpsService.getTeamsByUserId(id);
    }
}
