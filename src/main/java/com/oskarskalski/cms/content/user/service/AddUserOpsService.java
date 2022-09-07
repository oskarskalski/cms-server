package com.oskarskalski.cms.content.user.service;

import com.oskarskalski.cms.global.model.dto.UserRequest;
import com.oskarskalski.cms.global.exception.InvalidDataException;
import com.oskarskalski.cms.content.user.model.User;
import com.oskarskalski.cms.content.user.repo.UserRepo;
import com.oskarskalski.cms.global.security.PasswordConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AddUserOpsService {
    private UserRepo userRepo;

    @Autowired
    public AddUserOpsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public void addByObject(UserRequest userRequest) {
        if (userRepo.findByEmail(userRequest.getEmail()).isPresent())
            throw new InvalidDataException();

        if (userRequest.getNewPassword() == null)
            throw new InvalidDataException();

        if (userRequest.getFirstName() == null)
            throw new InvalidDataException();

        if (userRequest.getLastName() == null)
            throw new InvalidDataException();

        if (!userRequest.getNewPassword().equals(userRequest.getRepeatNewPassword()))
            throw new InvalidDataException();

        if (userRequest.getFirstName().length() < 2 ||
                userRequest.getFirstName().length() > 50)
            throw new InvalidDataException();

        if (userRequest.getLastName().length() < 2 ||
                userRequest.getLastName().length() > 50)
            throw new InvalidDataException();

        if (userRequest.getNewPassword().length() < 10 ||
                userRequest.getNewPassword().length() > 128)
            throw new InvalidDataException();

        PasswordConfiguration passwordConfiguration = new PasswordConfiguration();
        String password = passwordConfiguration.passwordEncoder()
                .encode(userRequest.getNewPassword());

        User user = new User();

        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(password);
        user.setSoftDelete(false);
        user.setCreatedAt(new Date());

        userRepo.save(user);
    }
}
