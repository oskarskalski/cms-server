package com.oskarskalski.cms.controller.team;

import com.oskarskalski.cms.dto.TeamDto;
import com.oskarskalski.cms.service.team.GetTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/team/")
public class GetTeamController {
    private final GetTeamService getTeamService;

    @Autowired
    public GetTeamController(GetTeamService getTeamService) {
        this.getTeamService = getTeamService;
    }

    @GetMapping("{id}")
    public TeamDto getTeamById(@PathVariable String id) {
        return getTeamService.getById(id);
    }

    @GetMapping("code/{id}")
    public String getTeamCode(
            @PathVariable String id,
            @RequestHeader(value = "Authorization", defaultValue = "") String header) {
        return getTeamService.getTeamCode(id, header);
    }

    @GetMapping("code/check/{id}")
    public boolean isCorrectTeamCode(@PathVariable String id, @RequestParam String code){
        return getTeamService.isCorrectTeamCode(id, code);
    }
}
