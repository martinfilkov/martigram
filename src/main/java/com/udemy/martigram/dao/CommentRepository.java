package com.udemy.martigram.dao;

import com.udemy.martigram.entity.GramComment;
import com.udemy.martigram.entity.GramPost;
import com.udemy.martigram.entity.GramUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<GramComment, Long> {
    Optional<GramComment> findByIdAndAuthor(Long id, GramUser author);
    List<GramComment> findByPost(GramPost post);
}
