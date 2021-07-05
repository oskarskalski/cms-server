package com.oskarskalski.cms.service.user;

import com.oskarskalski.cms.crud.operation.Add;
import com.oskarskalski.cms.exception.InvalidDataException;
import com.oskarskalski.cms.model.User;
import com.oskarskalski.cms.repo.UserRepo;
import com.oskarskalski.cms.security.PasswordConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddUserService implements Add<User> {
    private UserRepo userRepo;

    @Autowired
    public AddUserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public void addByObject(User user) {
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
}
