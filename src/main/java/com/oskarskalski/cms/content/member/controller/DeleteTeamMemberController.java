package com.oskarskalski.cms.content.member.controller;

import com.oskarskalski.cms.content.member.service.DeleteTeamMemberOpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teammate/")
public class DeleteTeamMemberController {
    private final DeleteTeamMemberOpsService deleteTeamMemberService;

    @Autowired
    public DeleteTeamMemberController(DeleteTeamMemberOpsService deleteTeamMemberService) {
        this.deleteTeamMemberService = deleteTeamMemberService;
    }

    @DeleteMapping("delete/{teamId}")
    public void softDelete(
            @PathVariable String teamId,
            @RequestHeader(value = "Authorization", defaultValue = "") String header) {

        deleteTeamMemberService.softDeleteByIdAndAuthorizationHeader(teamId, header);
    }

    @DeleteMapping("delete/{teamId}/hard")
    public void hardDelete(
            @PathVariable String teamId,
            @RequestHeader(value = "Authorization", defaultValue = "") String header) {

        deleteTeamMemberService.hardDeleteByIdAndAuthorizationHeader(teamId, header);
    }

    @DeleteMapping("delete/{teamId}/{userId}")
    public void deleteByTeamCreator(
            @PathVariable("teamId") String teamId,
            @PathVariable("userId") long userId,
            @RequestHeader(value = "Authorization", defaultValue = "") String header) {

        deleteTeamMemberService.deleteTeamMemberByTeamIdAndAuthorizationHeaderAndUserId(teamId, header, userId);
    }
}
