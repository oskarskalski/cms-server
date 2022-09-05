package com.oskarskalski.cms.content.user.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.oskarskalski.cms.crud.operation.SecuredUpdate;
import com.oskarskalski.cms.global.model.dto.UserRequest;
import com.oskarskalski.cms.global.exception.InvalidDataException;
import com.oskarskalski.cms.global.exception.NotFoundException;
import com.oskarskalski.cms.global.configuration.JwtConfiguration;
import com.oskarskalski.cms.content.user.model.User;
import com.oskarskalski.cms.content.user.repo.UserRepo;
import com.oskarskalski.cms.global.security.PasswordConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateUserOpsService implements SecuredUpdate<UserRequest> {
    private final UserRepo userRepo;
    private final JwtConfiguration jwtConfiguration = new JwtConfiguration();

    @Autowired
    public UpdateUserOpsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public void updateByObjectAndAuthorizationHeader(UserRequest updatedUser, String header) {
        DecodedJWT decodedJWT = jwtConfiguration.parse(header);
        long userId = Long.parseLong(decodedJWT.getClaim("id").asString());
        User user = userRepo.findById(userId)
                .orElseThrow(NotFoundException::new);

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
            } else {
                throw new InvalidDataException();
            }
        }

        userRepo.save(user);
    }
}
