package com.oskarskalski.cms.controller;

import com.oskarskalski.cms.dto.UserDto;
import com.oskarskalski.cms.dto.UserRequest;
import com.oskarskalski.cms.model.User;
import com.oskarskalski.cms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{id}")
    public UserDto getUserById(@PathVariable long id){
        return userService.getUserById(id);
    }

    @GetMapping("fullName/{id}")
    public String getUserFullNameById(@PathVariable long id){
        return userService.getUserFullNameById(id);
    }

    @PostMapping("add")
    public void addUser(@RequestBody User user){
        userService.addUser(user);
    }

    @DeleteMapping("delete/{id}")
    public void softDeleteUserById(@PathVariable long id){
        userService.softDeleteUserById(id);
    }

    @PutMapping("update")
    public void updateUser(@RequestBody UserRequest user,
                           @RequestHeader(value = "Authorization", defaultValue = "") String header){
        System.out.println(header);
        userService.updateUser(user, header);
    }
}
