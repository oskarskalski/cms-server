package com.oskarskalski.cms.service;

import com.oskarskalski.cms.configuration.JwtConfiguration;
import com.oskarskalski.cms.exception.AccessDeniedException;
import com.oskarskalski.cms.exception.NotFoundException;
import com.oskarskalski.cms.model.SignInAttempt;
import com.oskarskalski.cms.model.User;
import com.oskarskalski.cms.repo.SignInAttemptRepo;
import com.oskarskalski.cms.repo.UserRepo;
import com.oskarskalski.cms.security.PasswordConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserAuthenticationService {
    private final UserRepo userRepo;
    private final SignInAttemptRepo signInAttemptRepo;

    @Autowired
    public UserAuthenticationService(UserRepo userRepo, SignInAttemptRepo signInAttemptRepo) {
        this.userRepo = userRepo;
        this.signInAttemptRepo = signInAttemptRepo;
    }

    public User findUserByEmailAndPassword(String email, String password) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(NotFoundException::new);

        PasswordConfiguration passwordConfiguration = new PasswordConfiguration();

        SignInAttempt signInAttempt = new SignInAttempt();
        signInAttempt.setEmail(email);
        signInAttempt.setDateOfLastAttempt(new Date());

        boolean isPasswordsMatches = passwordConfiguration.passwordEncoder()
                .matches(password, user.getPassword());

        if (!isPasswordsMatches) {
            signInAttempt.setPositive(false);
            signInAttemptRepo.save(signInAttempt);

            throw new AccessDeniedException();
        }

        List<SignInAttempt> signInAttempts = signInAttemptRepo
                .findTop5ByEmailAndPositiveOrderByDateOfLastAttemptDesc(email, false);

        Date currentDate = null, getFirstDate = null;

        long differenceBetweenDates = 0;

        if (signInAttempts.size() != 0) {
            currentDate = signInAttempts.get(0).getDateOfLastAttempt();
            getFirstDate = signInAttempts.get(signInAttempts.size() - 1).getDateOfLastAttempt();

            differenceBetweenDates = (currentDate.getTime() - getFirstDate.getTime()) / 1000;
        }
        if (differenceBetweenDates != 0 && differenceBetweenDates < 60) {
            signInAttempt.setPositive(false);
            signInAttemptRepo.save(signInAttempt);

            throw new AccessDeniedException();


        }

        signInAttempt.setPositive(true);
        signInAttemptRepo.save(signInAttempt);
        return user;
    }
}
