package com.oskarskalski.cms.controller.teammate;

import com.oskarskalski.cms.model.TeamMember;
import com.oskarskalski.cms.service.teammember.AddTeamMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teammate/")
public class AddTeamMateController {
    private final AddTeamMemberService addTeamMemberService;

    @Autowired
    public AddTeamMateController(AddTeamMemberService addTeamMemberService) {
        this.addTeamMemberService = addTeamMemberService;
    }

    @PostMapping("add")
    public void addTeamMate(@RequestBody TeamMember teamMember) {
        addTeamMemberService.addTeamMember(teamMember);
    }

    @PostMapping("add/{id}")
    public void addTeamMateX(
            @PathVariable String id,
            @RequestParam String code,
            @RequestHeader(value = "Authorization", defaultValue = "") String header) {
        addTeamMemberService.addTeamMemberByTeamIdAndCode(id, code, header);
    }
}
