package com.oskarskalski.cms.controller.user;

import com.oskarskalski.cms.model.User;
import com.oskarskalski.cms.service.user.AddUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users/")
public class AddUserController {
    private final AddUserService addUserService;

    @Autowired
    public AddUserController(AddUserService addUserService) {
        this.addUserService = addUserService;
    }

    @PostMapping("add")
    public void addUser(@RequestBody User user){
        addUserService.add(user);
    }

}
