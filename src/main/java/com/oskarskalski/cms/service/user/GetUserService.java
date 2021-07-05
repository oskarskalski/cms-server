package com.oskarskalski.cms.service.user;

import com.oskarskalski.cms.crud.operation.Get;
import com.oskarskalski.cms.dto.UserDto;
import com.oskarskalski.cms.model.User;
import com.oskarskalski.cms.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetUserService implements Get<UserDto, Long> {
    private final UserRepo userRepo;

    @Autowired
    public GetUserService(UserRepo userRepo) {
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

    public String getFullNameById(long id) {
        User user = userRepo.findById(id)
                .orElseThrow(NullPointerException::new);

        return user.getFirstName() + " " + user.getLastName();
    }
}
