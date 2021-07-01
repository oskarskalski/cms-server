package com.oskarskalski.cms.controller.user;

import com.oskarskalski.cms.dto.UserDto;
import com.oskarskalski.cms.service.user.GetUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users/")
public class GetUserController {
    private final GetUserService getUserService;

    @Autowired
    public GetUserController(GetUserService getUserService) {
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
}
