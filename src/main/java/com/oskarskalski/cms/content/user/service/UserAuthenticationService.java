package com.oskarskalski.cms.content.user.service;

import com.oskarskalski.cms.global.exception.NotFoundException;
import com.oskarskalski.cms.content.user.model.User;
import com.oskarskalski.cms.content.user.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAuthenticationService {
    private final UserRepo userRepo;

    @Autowired
    public UserAuthenticationService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User findUserByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(NotFoundException::new);
    }

}
