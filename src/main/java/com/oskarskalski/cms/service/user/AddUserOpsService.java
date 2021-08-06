package com.oskarskalski.cms.service.user;

import com.oskarskalski.cms.crud.operation.Add;
import com.oskarskalski.cms.dto.UserRequest;
import com.oskarskalski.cms.exception.InvalidDataException;
import com.oskarskalski.cms.model.User;
import com.oskarskalski.cms.repo.UserRepo;
import com.oskarskalski.cms.security.PasswordConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AddUserOpsService implements Add<UserRequest> {
    private UserRepo userRepo;

    @Autowired
    public AddUserOpsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public void addByObject(UserRequest userRequest) {
        if (userRequest.getFirstName() == null ||
                userRequest.getFirstName().length() < 2 ||
                userRequest.getFirstName().length() > 50)
            throw new InvalidDataException();

        if (userRequest.getLastName() == null ||
                userRequest.getLastName().length() < 2 ||
                userRequest.getLastName().length() > 50)
            throw new InvalidDataException();

        if (userRequest.getNewPassword() == null ||
                userRequest.getNewPassword().length() < 10 ||
                userRequest.getNewPassword().length() > 128)
            throw new InvalidDataException();

        if(!userRequest.getNewPassword().equals(userRequest.getRepeatNewPassword()))
            throw new InvalidDataException();

//        if(!userRepo.findByEmail(userRequest.getEmail()).isPresent())
//            throw new InvalidDataException();

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
