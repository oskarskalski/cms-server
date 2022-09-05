package com.oskarskalski.cms.content.follow.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.oskarskalski.cms.content.article.repo.ArticleRepo;
import com.oskarskalski.cms.global.configuration.JwtConfiguration;
import com.oskarskalski.cms.crud.operation.SecuredAdd;
import com.oskarskalski.cms.global.exception.InvalidDataException;
import com.oskarskalski.cms.content.follow.model.Follow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AddFollowOpsService implements SecuredAdd<Follow> {
    private final ArticleRepo.FollowRepo followRepo;
    private final JwtConfiguration jwtConfiguration = new JwtConfiguration();

    @Autowired
    public AddFollowOpsService(ArticleRepo.FollowRepo followRepo) {
        this.followRepo = followRepo;
    }

    @Override
    public void addByObjectAndAuthorizationHeader(Follow follow, String header) {
        DecodedJWT decodedJWT = jwtConfiguration.parse(header);
        long userId = Long.parseLong(decodedJWT.getClaim("id").asString());

        if(userId == follow.getFollowingId())
            throw new InvalidDataException();

        if(follow.getFollowingId() <= 0)
            throw new InvalidDataException();

        Date date = new Date();
        follow.setDate(date);
        follow.setUserId(userId);

        followRepo.save(follow);
    }
}
