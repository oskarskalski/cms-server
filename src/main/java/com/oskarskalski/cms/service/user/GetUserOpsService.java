package com.oskarskalski.cms.service.user;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oskarskalski.cms.configuration.JwtConfiguration;
import com.oskarskalski.cms.crud.operation.Get;
import com.oskarskalski.cms.dto.ArticleDto;
import com.oskarskalski.cms.dto.TeamMemberDto;
import com.oskarskalski.cms.dto.UserDto;
import com.oskarskalski.cms.model.User;
import com.oskarskalski.cms.repo.UserRepo;
import com.oskarskalski.cms.service.article.GetArticleOpsService;
import com.oskarskalski.cms.service.follow.GetFollowOpsService;
import com.oskarskalski.cms.service.teammember.GetTeamMemberOpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.*;

@Service
public class GetUserOpsService implements Get<UserDto, Long> {
    private final UserRepo userRepo;

    private final GetFollowOpsService getFollowOpsService;
    private final GetTeamMemberOpsService getTeamMemberOpsService;
    private final GetArticleOpsService getArticleOpsService;

    @Autowired
    public GetUserOpsService(UserRepo userRepo,
                             @Lazy GetFollowOpsService getFollowOpsService,
                             @Lazy GetTeamMemberOpsService getTeamMemberOpsService,
                             @Lazy GetArticleOpsService getArticleOpsService) {
        this.userRepo = userRepo;
        this.getFollowOpsService = getFollowOpsService;
        this.getTeamMemberOpsService = getTeamMemberOpsService;
        this.getArticleOpsService = getArticleOpsService;
    }

    public UserDto getById(Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(NullPointerException::new);

        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());

        return userDto;
    }

    public Map<String, Object> getInformationAboutUserByHeaderAndId(String header, Optional<Long> userId) {
        Map<String, Object> map = new HashMap<>();
        long id;
        if (!userId.isPresent() || userId.get() == 0) {
            JwtConfiguration jwtConfiguration = new JwtConfiguration();
            DecodedJWT decodedJWT = jwtConfiguration.parse(header);
            id = Long.parseLong(decodedJWT.getClaim("id").asString());
        } else {
            id = userId.get();
        }

        List<ArticleDto> articles = getArticleOpsService.getAllArticlesByAuthorId(id);
        List<TeamMemberDto> teams = getTeamMemberOpsService.getTeamsByUserId(id);
        Map<String, Integer> followStatistics  = getFollowOpsService.getStatisticsByUserId(id);

        map.put("articles", articles);
        map.put("teams", teams);
        map.put("followStatistics", followStatistics);

        UserDto userDto = getById(id);
        map.put("userInformation", userDto);

        return map;
    }

    public String getFullNameById(long id) {
        User user = userRepo.findById(id)
                .orElseThrow(NullPointerException::new);

        return user.getFirstName() + " " + user.getLastName();
    }
}
