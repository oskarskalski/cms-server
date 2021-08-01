package com.oskarskalski.cms.controller.team;


import com.oskarskalski.cms.service.team.DeleteTeamOpsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/team/")
public class DeleteTeamController {
    private final DeleteTeamOpsService deleteTeamService;

    public DeleteTeamController(DeleteTeamOpsService deleteTeamService) {
        this.deleteTeamService = deleteTeamService;
    }

    @DeleteMapping("/{id}")
    public void softDelete(
            @PathVariable String id,
            @RequestHeader(value = "Authorization", defaultValue = "") String header) {
        deleteTeamService.softDeleteByIdAndAuthorizationHeader(id, header);
    }

    @DeleteMapping("/{id}/hard")
    public void hardDelete(
            @PathVariable String id,
            @RequestHeader(value = "Authorization", defaultValue = "") String header) {
        deleteTeamService.hardDeleteByIdAndAuthorizationHeader(id, header);
    }
}
