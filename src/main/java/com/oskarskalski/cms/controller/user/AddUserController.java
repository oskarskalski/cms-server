package com.oskarskalski.cms.controller.user;

import com.oskarskalski.cms.model.User;
import com.oskarskalski.cms.service.user.AddUserOpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users/")
public class AddUserController {
    private final AddUserOpsService addUserService;

    @Autowired
    public AddUserController(AddUserOpsService addUserService) {
        this.addUserService = addUserService;
    }

    @PostMapping("add")
    public void addUser(@RequestBody User user){
        System.out.println("test");
        addUserService.addByObject(user);
    }

}
