package com.oskarskalski.cms.controller.user;

import com.oskarskalski.cms.dto.UserRequest;
import com.oskarskalski.cms.service.user.UpdateUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/")
public class UpdateUserController {
    private final UpdateUser updateUser;

    @Autowired
    public UpdateUserController(UpdateUser updateUser) {
        this.updateUser = updateUser;
    }

    @PutMapping("update")
    public void updateUserByUserRequestAndHeader(
            @RequestBody UserRequest user,
            @RequestHeader(value = "Authorization", defaultValue = "") String header) {
        updateUser.updateByDtoAndHeader(user, header);
    }
}
