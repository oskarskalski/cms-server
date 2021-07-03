package com.oskarskalski.cms.controller.user;

import com.oskarskalski.cms.dto.UserRequest;
import com.oskarskalski.cms.service.user.UpdateUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/")
public class UpdateUserController {
    private final UpdateUserService updateUser;

    @Autowired
    public UpdateUserController(UpdateUserService updateUser) {
        this.updateUser = updateUser;
    }

    @PutMapping("update")
    public void updateUserByUserRequestAndHeader(
            @RequestBody UserRequest user,
            @RequestHeader(value = "Authorization", defaultValue = "") String header) {
        updateUser.updateByDtoAndHeader(user, header);
    }
}
