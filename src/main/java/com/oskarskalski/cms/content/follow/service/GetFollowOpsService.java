package com.oskarskalski.cms.content.follow.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.oskarskalski.cms.content.article.repo.ArticleRepo;
import com.oskarskalski.cms.global.configuration.JwtConfiguration;
import com.oskarskalski.cms.global.exception.NotFoundException;
import com.oskarskalski.cms.content.follow.model.Follow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GetFollowOpsService {
    private final ArticleRepo.FollowRepo followRepo;
    private final JwtConfiguration jwtConfiguration = new JwtConfiguration();

    @Autowired
    public GetFollowOpsService(ArticleRepo.FollowRepo followRepo) {
        this.followRepo = followRepo;
    }

    public List<Follow> getAllObjectsByAuthorizationToken(String header){
        DecodedJWT decodedJWT = jwtConfiguration.parse(header);
        long userId = Long.parseLong(decodedJWT.getClaim("id").asString());

        return getAllObjectsByUserId(userId);
    }

    public List<Follow> getAllObjectsByUserId(long id){
        return followRepo.findAllByUserId(id)
                .orElseThrow(NotFoundException::new);
    }
    public List<Follow> getAllObjectsByFollowingId(long id){
        return followRepo.findAllByFollowingId(id)
                .orElseThrow(NotFoundException::new);
    }

    public Map<String, Integer> getStatisticsByUserId(long id){
        int follows = getAllObjectsByUserId(id).size();
        int followings = getAllObjectsByFollowingId(id).size();
        Map<String, Integer> map = new HashMap<>();
        map.put("follows", follows);
        map.put("followings", followings);
        return map;
    }
}
