package com.oskarskalski.cms.service.user;

import com.oskarskalski.cms.dto.UserRequest;
import com.oskarskalski.cms.exception.InvalidDataException;
import com.oskarskalski.cms.operations.Update;
import com.oskarskalski.cms.json.JwtConfiguration;
import com.oskarskalski.cms.model.User;
import com.oskarskalski.cms.repo.UserRepo;
import com.oskarskalski.cms.security.PasswordConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateUser implements Update<UserRequest> {
    private final UserRepo userRepo;
    @Autowired
    public UpdateUser(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public void updateByDtoAndHeader(UserRequest updatedUser, String header) {
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
