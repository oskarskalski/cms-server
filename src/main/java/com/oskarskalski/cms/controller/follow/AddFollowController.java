package com.oskarskalski.cms.controller.follow;

import com.oskarskalski.cms.model.Follow;
import com.oskarskalski.cms.service.follow.AddFollowOpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/follow/")
public class AddFollowController {
    private final AddFollowOpsService addFollowOpsService;

    @Autowired
    public AddFollowController(AddFollowOpsService addFollowOpsService) {
        this.addFollowOpsService = addFollowOpsService;
    }

    @PostMapping("add")
    public void addFollowByFollowObjectAndAuthorizationToken(Follow follow, @RequestHeader("Authorization")String authorizationToken){
        addFollowOpsService.addByObjectAndAuthorizationHeader(follow, authorizationToken);
    }
}
