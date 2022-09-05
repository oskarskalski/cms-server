package com.oskarskalski.cms.content.member.controller;

import com.oskarskalski.cms.content.team.model.dto.CodeDto;
import com.oskarskalski.cms.content.member.model.TeamMember;
import com.oskarskalski.cms.content.member.service.AddTeamMemberOpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teamMember/")
public class AddTeamMemberController {
    private final AddTeamMemberOpsService addTeamMemberService;

    @Autowired
    public AddTeamMemberController(AddTeamMemberOpsService addTeamMemberService) {
        this.addTeamMemberService = addTeamMemberService;
    }

    @PostMapping("add/unsecured")
    public void addTeamMember(@RequestBody TeamMember teamMember) {
        addTeamMemberService.addByObject(teamMember);
    }

    @PostMapping("add")
    public void addTeamMember(
            @RequestParam CodeDto codeDto,
            @RequestHeader(value = "Authorization", defaultValue = "") String header) {
        addTeamMemberService.addByObjectAndAuthorizationHeader(codeDto, header);
    }
}
