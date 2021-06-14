package com.oskarskalski.cms.service;

import com.oskarskalski.cms.dto.UserDto;
import com.oskarskalski.cms.model.User;
import com.oskarskalski.cms.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public void addUser(User user){
        userRepo.save(user);
    }

    public UserDto getUserById(long id){
        User user = userRepo.findById(id)
                .orElseThrow(NullPointerException::new);

        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());

        return userDto;
    }

    public void softDeleteUserById(long id) {
        User user = userRepo.findById(id)
                .orElseThrow(NullPointerException::new);

        user.setSoftDelete(true);

        userRepo.save(user);
    }

    public String getUserFullNameById(long id) {
        User user = userRepo.findById(id)
                .orElseThrow(NullPointerException::new);

        return user.getFirstName() + " " + user.getLastName();
    }
}
