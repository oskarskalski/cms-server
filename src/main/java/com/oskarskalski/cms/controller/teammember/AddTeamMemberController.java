package com.oskarskalski.cms.controller.teammember;

import com.oskarskalski.cms.model.TeamMember;
import com.oskarskalski.cms.service.teammember.AddTeamMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teamMember/")
public class AddTeamMemberController {
    private final AddTeamMemberService addTeamMemberService;

    @Autowired
    public AddTeamMemberController(AddTeamMemberService addTeamMemberService) {
        this.addTeamMemberService = addTeamMemberService;
    }

    @PostMapping("add")
    public void addTeamMember(@RequestBody TeamMember teamMember) {
        System.out.println("test");
        addTeamMemberService.addByObject(teamMember);
    }

    @PostMapping("add/{id}")
    public void addTeamMember(
            @PathVariable String id,
            @RequestParam String code,
            @RequestHeader(value = "Authorization", defaultValue = "") String header) {
        addTeamMemberService.addTeamMemberByTeamIdAndCode(id, code, header);
    }
}
