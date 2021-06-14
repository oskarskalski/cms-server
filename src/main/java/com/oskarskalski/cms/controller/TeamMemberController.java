package com.oskarskalski.cms.controller;

import com.oskarskalski.cms.model.TeamMember;
import com.oskarskalski.cms.service.TeamMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/teamMember/")
public class TeamMemberController {
    private final TeamMemberService teamMemberService;

    @Autowired
    public TeamMemberController(TeamMemberService teamMemberService) {
        this.teamMemberService = teamMemberService;
    }

    @PostMapping("add")
    public void test(@RequestBody TeamMember teamMember){
        teamMemberService.add(teamMember);
    }
}
