package com.oskarskalski.cms.service;

import com.oskarskalski.cms.dto.UserDto;
import com.oskarskalski.cms.dto.UserRequest;
import com.oskarskalski.cms.exception.InvalidDataException;
import com.oskarskalski.cms.json.JwtConfiguration;
import com.oskarskalski.cms.model.User;
import com.oskarskalski.cms.repo.UserRepo;
import com.oskarskalski.cms.security.PasswordConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public void addUser(User user) {
        if (user.getFirstName() == null || user.getFirstName().length() < 2 || user.getFirstName().length() > 50)
            throw new InvalidDataException();

        if (user.getLastName() == null || user.getLastName().length() < 2 || user.getLastName().length() > 50)
            throw new InvalidDataException();

        if (user.getPassword() == null || user.getPassword().length() < 10 || user.getPassword().length() > 128)
            throw new InvalidDataException();

        PasswordConfiguration passwordConfiguration = new PasswordConfiguration();
        String password = passwordConfiguration.passwordEncoder().encode(user.getPassword());
        user.setPassword(password);

        userRepo.save(user);
    }

    public UserDto getUserById(long id) {
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

    public void updateUser(UserRequest updatedUser, String header) {
        JwtConfiguration jwtConfiguration = new JwtConfiguration();
        String email = jwtConfiguration.parse(header).getSubject();
        User user = userRepo.findByEmail(email)
                .orElseThrow();

        if (updatedUser.getFirstName() != null &&
                updatedUser.getFirstName().length() >= 2 && updatedUser.getFirstName().length() <= 50)
            user.setFirstName(updatedUser.getFirstName());

        if (updatedUser.getLastName() != null &&
                updatedUser.getLastName().length() >= 2 && updatedUser.getLastName().length() <= 50)
            user.setLastName(updatedUser.getLastName());

        if (updatedUser.getEmail() != null)
            user.setEmail(updatedUser.getEmail());

        if (updatedUser.getOldPassword() != null && updatedUser.getNewPassword() != null &&
                updatedUser.getNewPassword().length() >= 10 && updatedUser.getNewPassword().length() <= 128) {
            PasswordConfiguration passwordConfiguration = new PasswordConfiguration();

            if (passwordConfiguration.passwordEncoder()
                    .matches(updatedUser.getNewPassword(), user.getPassword())) {
                String encodeNewPassword = passwordConfiguration.passwordEncoder().encode(updatedUser.getNewPassword());
                user.setPassword(encodeNewPassword);
            }else{
                throw new InvalidDataException();
            }
        }

        userRepo.save(user);
    }
}
