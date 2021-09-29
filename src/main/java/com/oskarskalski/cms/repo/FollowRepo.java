package com.oskarskalski.cms.repo;

import com.oskarskalski.cms.model.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepo extends JpaRepository<Follow, Long> {
    Optional<List<Follow>> findAllByUserId(long userId);
    Optional<List<Follow>> findAllByFollowingId(long userId);
}