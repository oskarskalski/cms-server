package com.oskarskalski.cms.content.team.controller;

import com.oskarskalski.cms.content.team.model.dto.TeamDto;
import com.oskarskalski.cms.content.team.service.AddTeamOpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/team/")
public class AddTeamController {
    private final AddTeamOpsService addTeamService;

    @Autowired
    public AddTeamController(AddTeamOpsService addTeamService) {
        this.addTeamService = addTeamService;
    }
    @PostMapping("add")
    public void test(@RequestBody TeamDto teamDto,
                     @RequestHeader(value = "Authorization", defaultValue = "") String header) {
        addTeamService.addByObjectAndAuthorizationHeader(teamDto, header);
    }

}
