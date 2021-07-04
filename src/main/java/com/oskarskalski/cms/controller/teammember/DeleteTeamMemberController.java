package com.oskarskalski.cms.controller.teammember;

import com.oskarskalski.cms.service.teammember.DeleteTeamMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teammate/")
public class DeleteTeamMemberController {
    private final DeleteTeamMemberService deleteTeamMemberService;

    @Autowired
    public DeleteTeamMemberController(DeleteTeamMemberService deleteTeamMemberService) {
        this.deleteTeamMemberService = deleteTeamMemberService;
    }

    @DeleteMapping("{teamId}")
    public void deleteByUser(
            @PathVariable String teamId,
            @RequestHeader(value = "Authorization", defaultValue = "") String header) {

        deleteTeamMemberService.deleteTeamMemberByTeamIdAndAuthorizationHeader(teamId, header);
    }

    @DeleteMapping("{teamId}/{userId}")
    public void deleteByTeamCreator(
            @PathVariable("teamId") String teamId,
            @PathVariable("userId") long userId,
            @RequestHeader(value = "Authorization", defaultValue = "") String header) {

        deleteTeamMemberService.deleteTeamMemberByTeamIdAndAuthorizationHeaderAndUserId(teamId, header, userId);
    }
}
