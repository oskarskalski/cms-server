package com.oskarskalski.cms.controller;

import com.oskarskalski.cms.dto.TeamDto;
import com.oskarskalski.cms.model.Team;
import com.oskarskalski.cms.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/team/")
public class TeamController {
    private final TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping("add")
    public void test(@RequestBody Team team, @RequestHeader("Authorization")String header){
        teamService.createTeam(team, header);
    }

    @GetMapping("{id}")
    public TeamDto getTeamById(@PathVariable String id, @RequestHeader("Authorization")String header){
        return teamService.getTeamById(id);
    }

    @PutMapping("generateCode")
    public void generateTeamCode(String id, @RequestHeader("Authorization")String header){
        teamService.generateCodeForJoiningTeam(id, header);
    }
}
