package com.oskarskalski.cms.content.user.controller;

import com.oskarskalski.cms.content.user.model.dto.UserDto;
import com.oskarskalski.cms.content.user.service.GetUserOpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users/")
public class GetUserController {
    private final GetUserOpsService getUserService;

    @Autowired
    public GetUserController(GetUserOpsService getUserService) {
        this.getUserService = getUserService;
    }

    @GetMapping("{id}")
    public UserDto getUserById(@PathVariable long id){
        return getUserService.getById(id);
    }

    @GetMapping("fullName/{id}")
    public String getUserFullNameById(@PathVariable long id){
        return getUserService.getFullNameById(id);
    }

    @GetMapping("profile/{id}")
    public Map<String, Object> getUserProfile(@RequestHeader("Authorization")String header, @PathVariable Optional<Long> id){
        return getUserService.getInformationAboutUserByHeaderAndId(header, id);
    }
}