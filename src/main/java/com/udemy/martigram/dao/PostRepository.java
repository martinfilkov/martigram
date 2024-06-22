package com.udemy.martigram.dao;

import com.udemy.martigram.entity.GramPost;
import com.udemy.martigram.entity.GramUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<GramPost, Long> {
    List<GramPost> findByAuthor(GramUser author);
    Optional<GramPost> findByIdAndAuthor(long id, GramUser author);
}
