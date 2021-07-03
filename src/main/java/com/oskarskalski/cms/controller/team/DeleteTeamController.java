package com.oskarskalski.cms.controller.team;


import com.oskarskalski.cms.service.team.DeleteTeamService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/team/")
public class DeleteTeamController {
    private final DeleteTeamService deleteTeamService;

    public DeleteTeamController(DeleteTeamService deleteTeamService) {
        this.deleteTeamService = deleteTeamService;
    }

    @DeleteMapping("/{id}")
    public void softDelete(
            @PathVariable String id,
            @RequestHeader(value = "Authorization", defaultValue = "") String header) {
        deleteTeamService.softDeleteByIdAndHeader(id, header);
    }

    @DeleteMapping("/{id}/hard")
    public void hardDelete(
            @PathVariable String id,
            @RequestHeader(value = "Authorization", defaultValue = "") String header) {
        deleteTeamService.hardDeleteByIdAndHeader(id, header);
    }
}
