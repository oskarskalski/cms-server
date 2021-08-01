package com.oskarskalski.cms.controller.team;

import com.oskarskalski.cms.dto.TeamDto;
import com.oskarskalski.cms.service.team.AddTeamOpsService;
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
