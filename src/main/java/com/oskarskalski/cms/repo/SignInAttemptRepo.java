package com.oskarskalski.cms.repo;

import com.oskarskalski.cms.model.SignInAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SignInAttemptRepo extends JpaRepository<SignInAttempt, Long> {
    SignInAttempt findByEmail(String email);
    List<SignInAttempt> findTop5ByEmailAndPositiveOrderByDateOfLastAttemptDesc(String email, boolean positive);
}
