package com.oskarskalski.cms.content.follow.controller;

import com.oskarskalski.cms.content.follow.model.Follow;
import com.oskarskalski.cms.content.follow.service.GetFollowOpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/follow/")
public class GetFollowController {
    private final GetFollowOpsService getFollowOpsService;

    @Autowired
    public GetFollowController(GetFollowOpsService getFollowOpsService) {
        this.getFollowOpsService = getFollowOpsService;
    }

    @GetMapping("/secure")
    public List<Follow> getFollowsByAuthorizationToken(@RequestHeader("Authorization") String token){
        return getFollowOpsService.getAllObjectsByAuthorizationToken(token);
    }

    @GetMapping("{id}")
    public List<Follow> getFollowsByUserId(@PathVariable long id){
        return getFollowOpsService.getAllObjectsByUserId(id);
    }
    @GetMapping("{id}/following")
    public List<Follow> getFollowsByFollowingId(@PathVariable long id){
        return getFollowOpsService.getAllObjectsByUserId(id);
    }

    @GetMapping("statistics/{id}")
    public Map<String, Integer> getStatistics(@PathVariable long id){
        return getFollowOpsService.getStatisticsByUserId(id);
    }
}
