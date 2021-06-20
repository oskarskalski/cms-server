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
    public void test(@RequestBody TeamDto teamDto,
                     @RequestHeader(value = "Authorization", defaultValue = "") String header) {
        teamService.createTeam(teamDto, header);
    }

    @GetMapping("{id}")
    public TeamDto getTeamById(@PathVariable String id,
                               @RequestHeader(value = "Authorization", defaultValue = "") String header) {
        return teamService.getTeamById(id);
    }

    @PutMapping("updateCode/{id}")
    public void updateTeamCode(@PathVariable String id,
                               @RequestHeader(value = "Authorization", defaultValue = "") String header) {
        teamService.updateTeamCode(id, header);
    }

    @PutMapping("update/{id}")
    public void updateTeam(@PathVariable String id,
                           @RequestBody TeamDto teamDto,
                           @RequestHeader(value = "Authorization", defaultValue = "") String header) {
        teamService.updateTeam(id, teamDto, header);
    }

    @PostMapping("join/{id}")
    public void joinTeamByIdAndCode(@PathVariable String id,
                                    @RequestBody String code,
                                    @RequestHeader(value = "Authorization", defaultValue = "") String header) {
        teamService.joinTeamByIdAndSecretCode(id, code, header);
    }
}
