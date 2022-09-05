package com.oskarskalski.cms.content.team.controller;


import com.oskarskalski.cms.content.team.service.DeleteTeamOpsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/team/")
public class DeleteTeamController {
    private final DeleteTeamOpsService deleteTeamService;

    public DeleteTeamController(DeleteTeamOpsService deleteTeamService) {
        this.deleteTeamService = deleteTeamService;
    }

    @DeleteMapping("delete/{id}")
    public void softDelete(
            @PathVariable String id,
            @RequestHeader(value = "Authorization", defaultValue = "") String header) {
        deleteTeamService.softDeleteByIdAndAuthorizationHeader(id, header);
    }

    @DeleteMapping("delete/{id}/hard")
    public void hardDelete(
            @PathVariable String id,
            @RequestHeader(value = "Authorization", defaultValue = "") String header) {
        deleteTeamService.hardDeleteByIdAndAuthorizationHeader(id, header);
    }
}
