package com.oskarskalski.cms.service;

import com.oskarskalski.cms.exception.AccessDeniedException;
import com.oskarskalski.cms.model.SignInAttempt;
import com.oskarskalski.cms.model.User;
import com.oskarskalski.cms.repo.SignInAttemptRepo;
import com.oskarskalski.cms.security.PasswordConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AttemptSignInService {
    private final SignInAttemptRepo signInAttemptRepo;

    @Autowired
    public AttemptSignInService(SignInAttemptRepo signInAttemptRepo) {
        this.signInAttemptRepo = signInAttemptRepo;
    }

    public boolean attemptSignIn(User user, String password) {
        PasswordConfiguration passwordConfiguration = new PasswordConfiguration();

        SignInAttempt signInAttempt = new SignInAttempt();
        signInAttempt.setEmail(user.getEmail());
        signInAttempt.setDateOfLastAttempt(new Date());

        boolean isPasswordsMatches = passwordConfiguration.passwordEncoder()
                .matches(password, user.getPassword());

        if (!isPasswordsMatches) {
            return failedSignInAttempt(signInAttempt);
        }

        List<SignInAttempt> signInAttempts = signInAttemptRepo
                .findTop5ByEmailAndPositiveOrderByDateOfLastAttemptDesc(user.getEmail(), false);


        if (signInAttempts.size() != 0) {
            Date currentDate = signInAttempts.get(0).getDateOfLastAttempt();
            Date getFirstDate = signInAttempts.get(signInAttempts.size() - 1).getDateOfLastAttempt();

            long differenceBetweenDates = (currentDate.getTime() - getFirstDate.getTime()) / 1000;

            if (differenceBetweenDates < 60) {
                return failedSignInAttempt(signInAttempt);
            }
        }

        signInAttempt.setPositive(true);
        signInAttemptRepo.save(signInAttempt);
        return true;
    }


    private boolean failedSignInAttempt(SignInAttempt signInAttempt) {
        signInAttempt.setPositive(false);
        signInAttemptRepo.save(signInAttempt);

        return false;
    }
}
