package com.oskarskalski.cms.service.user;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.oskarskalski.cms.configuration.JwtConfiguration;
import com.oskarskalski.cms.crud.operation.Get;
import com.oskarskalski.cms.dto.UserDto;
import com.oskarskalski.cms.model.User;
import com.oskarskalski.cms.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class GetUserOpsService implements Get<UserDto, Long> {
    private final UserRepo userRepo;

    @Autowired
    public GetUserOpsService(UserRepo userRepo) {
        this.userRepo = userRepo;
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

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", header);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);

        try {
            ResponseEntity<List> articles = restTemplate.exchange(System.getenv("SITE_URL") + "/api/article/all/author/" + id, HttpMethod.GET, entity, List.class);
            if (articles.getStatusCodeValue() == 200)
                map.put("articles", articles.getBody());
        } catch (HttpClientErrorException e) {
            System.out.println("Not found");
        }

        try {
            ResponseEntity<List> teams = restTemplate.exchange(System.getenv("SITE_URL") + "/api/teamMember/" + id, HttpMethod.GET, entity, List.class);
            if (teams.getStatusCodeValue() == 200)
                map.put("teams", teams.getBody());
        } catch (HttpClientErrorException e) {
            System.out.println("Not found");
        }

        try {
            ResponseEntity<Map> followStatistics = restTemplate.exchange(System.getenv("SITE_URL") + "/api/follow/statistics/" + id, HttpMethod.GET, entity, Map.class);
            if (followStatistics.getStatusCodeValue() == 200)
                map.put("followStatistics", followStatistics.getBody());
        } catch (HttpClientErrorException e) {
            System.out.println("Not found");
        }
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
