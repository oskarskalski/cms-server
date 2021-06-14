package com.oskarskalski.cms.service;

import com.oskarskalski.cms.model.User;
import com.oskarskalski.cms.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAuthenticationService {
    private final UserRepo userRepo;

    @Autowired
    public UserAuthenticationService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User getUserByEmail(String email){
        return userRepo.findByEmail(email)
                .orElseThrow(NullPointerException::new);
    }
}
