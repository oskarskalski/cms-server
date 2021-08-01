package com.oskarskalski.cms.controller.user;

import com.oskarskalski.cms.service.user.DeleteUserOpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/")
public class DeleteUserController {
    private final DeleteUserOpsService deleteUserService;

    @Autowired
    public DeleteUserController(DeleteUserOpsService deleteUserService) {
        this.deleteUserService = deleteUserService;
    }

    @DeleteMapping("delete/{id}")
    public void softDeleteUserById(
            @PathVariable long id,
            @RequestHeader(value = "Authorization", defaultValue = "") String header) {
        deleteUserService.softDeleteByIdAndAuthorizationHeader(id, header);
    }

    @DeleteMapping("deleteAll/{id}")
    public void hardDeleteUserById(
            @PathVariable long id,
            @RequestHeader(value = "Authorization", defaultValue = "") String header) {
        deleteUserService.hardDeleteByIdAndAuthorizationHeader(id, header);
    }
}
