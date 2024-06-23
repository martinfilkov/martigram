package com.udemy.martigram.dao;

import com.udemy.martigram.entity.GramLike;
import com.udemy.martigram.entity.GramPost;
import com.udemy.martigram.entity.GramUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<GramLike, Long> {
    Optional<GramLike> findByUserAndPost(GramUser user, GramPost post);
    int countByPost(GramPost post);
}
