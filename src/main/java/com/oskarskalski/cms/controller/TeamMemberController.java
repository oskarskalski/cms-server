package com.oskarskalski.cms.controller;

import com.oskarskalski.cms.model.TeamMember;
import com.oskarskalski.cms.service.TeamMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teamMember/")
public class TeamMemberController {
    private final TeamMemberService teamMemberService;

    @Autowired
    public TeamMemberController(TeamMemberService teamMemberService) {
        this.teamMemberService = teamMemberService;
    }

    @PostMapping("add")
    public void addMember(@RequestBody TeamMember teamMember){
        System.out.println(teamMember.getUserId());
        teamMemberService.add(teamMember);
    }

    @GetMapping("creator/{id}")
    public long getTeamCreatorByTeamId(@PathVariable String id){
        return teamMemberService.getTeamCreatorIdByTeamId(id);
    }
}
