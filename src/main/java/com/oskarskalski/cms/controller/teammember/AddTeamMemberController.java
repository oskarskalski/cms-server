package com.oskarskalski.cms.controller.teammember;

import com.oskarskalski.cms.dto.CodeDto;
import com.oskarskalski.cms.model.TeamMember;
import com.oskarskalski.cms.service.teammember.AddTeamMemberOpsService;
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
