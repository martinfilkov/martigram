package com.udemy.martigram.service;

import com.udemy.martigram.dao.FollowerRepository;
import com.udemy.martigram.dao.UserRepository;
import com.udemy.martigram.entity.*;
import com.udemy.martigram.security.AuthenticatedUserProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FollowServiceTest {
    @InjectMocks
    private FollowService followService;

    @Mock
    private FollowerRepository followerRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticatedUserProvider authenticatedUserProvider;

    private GramUser follower;
    private GramFollow follow;
    private GramUser followed;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        follower = GramUser.builder()
                .id(1L)
                .email("user@test.com")
                .username("user")
                .role(new GramRole(RoleType.USER))
                .build();

        followed = GramUser.builder()
                .id(2L)
                .email("admin@test.com")
                .username("admin")
                .role(new GramRole(RoleType.ADMIN))
                .build();

        follow = GramFollow.builder()
                .follower(follower)
                .followed(followed)
                .build();
    }

    @Test
    public void shouldFollow(){
        when(authenticatedUserProvider.getAuthenticatedUser()).thenReturn(follower);
        when(userRepository.findById(1L)).thenReturn(Optional.of(follower));
        when(userRepository.findById(2L)).thenReturn(Optional.of(followed));
        when(followerRepository.findByFollowerAndFollowed(follower, followed)).thenReturn(Optional.empty());

        followService.followUser(2L);

        verify(followerRepository, times(1)).save(any(GramFollow.class));
    }

    @Test
    public void shouldUnfollow(){
        when(authenticatedUserProvider.getAuthenticatedUser()).thenReturn(follower);
        when(userRepository.findById(1L)).thenReturn(Optional.of(follower));
        when(userRepository.findById(2L)).thenReturn(Optional.of(followed));
        when(followerRepository.findByFollowerAndFollowed(follower, followed)).thenReturn(Optional.of(follow));

        followService.unfollowUser(2L);

        verify(followerRepository, times(1)).delete(follow);
    }
}