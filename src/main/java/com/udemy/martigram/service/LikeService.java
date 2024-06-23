package com.udemy.martigram.service;

import com.udemy.martigram.dao.LikeRepository;
import com.udemy.martigram.dao.PostRepository;
import com.udemy.martigram.entity.GramLike;
import com.udemy.martigram.entity.GramPost;
import com.udemy.martigram.entity.GramUser;
import com.udemy.martigram.exception.NotFoundException;
import com.udemy.martigram.exception.UniqueConstraintException;
import com.udemy.martigram.security.AuthenticatedUserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    public void like(long post_id){
        GramUser user = authenticatedUserProvider.getAuthenticatedUser();

        Optional<GramPost> post = postRepository.findById(post_id);

        if(post.isEmpty())
            throw new NotFoundException("Post with id " + post_id + " not found");

        if(likeRepository.findByUserAndPost(user, post.get()).isPresent())
            throw new UniqueConstraintException("Post already liked");

        GramLike like = GramLike.builder()
                .user(user)
                .post(post.get())
                .build();

        likeRepository.save(like);
    }

    public void removeLike(long post_id){
        GramUser user = authenticatedUserProvider.getAuthenticatedUser();

        Optional<GramPost> post = postRepository.findById(post_id);

        if(post.isEmpty())
            throw new NotFoundException("Post with id " + post_id + " not found");

        Optional<GramLike> like = likeRepository.findByUserAndPost(user, post.get());

        if(like.isEmpty()) throw new NotFoundException("Like relationship not found");

        likeRepository.delete(like.get());
    }
}
