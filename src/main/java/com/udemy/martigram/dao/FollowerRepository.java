package com.udemy.martigram.dao;

import com.udemy.martigram.entity.GramFollow;
import com.udemy.martigram.entity.GramUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowerRepository extends JpaRepository<GramFollow, Long> {
    Optional<GramFollow> findByFollowerAndFollowed(GramUser follower, GramUser followed);
    int countByFollowed(GramUser followed);
    int countByFollower(GramUser follower);
}
