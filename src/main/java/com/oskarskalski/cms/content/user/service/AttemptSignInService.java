package com.oskarskalski.cms.content.user.service;

import com.oskarskalski.cms.content.user.model.SignInAttempt;
import com.oskarskalski.cms.content.user.model.User;
import com.oskarskalski.cms.content.user.repo.SignInAttemptRepo;
import com.oskarskalski.cms.global.security.PasswordConfiguration;
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


        if (!signInAttempts.isEmpty()) {
            Date currentDate = signInAttempts.get(0).getDateOfLastAttempt();
            Date getFirstDate = signInAttempts.get(signInAttempts.size() - 1).getDateOfLastAttempt();

            long differenceBetweenDates = (currentDate.getTime() - getFirstDate.getTime()) / 1000;

            //If someone tries to sign in and he failed minimum three times then send a email to the account
            // owner that someone is trying to get access to this account

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
