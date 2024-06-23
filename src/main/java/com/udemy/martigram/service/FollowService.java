package com.udemy.martigram.service;

import com.udemy.martigram.dao.FollowerRepository;
import com.udemy.martigram.dao.UserRepository;
import com.udemy.martigram.entity.GramFollow;
import com.udemy.martigram.entity.GramUser;
import com.udemy.martigram.exception.InvalidActionException;
import com.udemy.martigram.exception.NotFoundException;
import com.udemy.martigram.exception.UniqueConstraintException;
import com.udemy.martigram.security.AuthenticatedUserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowerRepository followerRepository;
    private final UserRepository userRepository;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    public void followUser(long followedId){
        GramUser follower = authenticatedUserProvider.getAuthenticatedUser();
        if(follower.getId() == followedId) throw new InvalidActionException("Unable to follow yourself");

        Optional<GramUser> followed = userRepository.findById(followedId);
        if(followed.isEmpty()) throw new NotFoundException("User with id " + followedId + " not found");

        if (followerRepository.findByFollowerAndFollowed(follower, followed.get()).isPresent())
            throw new UniqueConstraintException("User already followed");

        GramFollow follow =  GramFollow.builder()
                .follower(follower)
                .followed(followed.get())
                .build();

        followerRepository.save(follow);
    }

    public void unfollowUser(long followedId){
        GramUser follower = authenticatedUserProvider.getAuthenticatedUser();

        Optional<GramUser> followed = userRepository.findById(followedId);
        if(followed.isEmpty()) throw new NotFoundException("User with id " + followedId + " not found");

        Optional<GramFollow> follow = followerRepository.findByFollowerAndFollowed(follower, followed.get());

        if (follow.isEmpty()) throw new NotFoundException("Follow relationship not found");

        followerRepository.delete(follow.get());
    }
}
