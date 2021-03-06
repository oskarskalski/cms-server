package com.oskarskalski.cms.controller.team;

import com.oskarskalski.cms.dto.TeamDto;
import com.oskarskalski.cms.service.team.UpdateTeamOpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/team/")
public class UpdateTeamController {
    private final UpdateTeamOpsService updateTeamService;

    @Autowired
    public UpdateTeamController(UpdateTeamOpsService updateTeamService) {
        this.updateTeamService = updateTeamService;
    }

    @PutMapping("update/code/{id}")
    public void updateTeamCode(
            @PathVariable String id,
            @RequestHeader(value = "Authorization", defaultValue = "") String header) {
        updateTeamService.updateTeamCode(id, header);
    }

    @PutMapping("update")
    public void updateTeam(
            @RequestBody TeamDto teamDto,
            @RequestHeader(value = "Authorization", defaultValue = "") String header) {
        updateTeamService.updateByObjectAndAuthorizationHeader(teamDto, header);
    }
}
