package com.udemy.martigram.service;

import com.udemy.martigram.dao.LikeRepository;
import com.udemy.martigram.dao.PostRepository;
import com.udemy.martigram.entity.*;
import com.udemy.martigram.security.AuthenticatedUserProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LikeServiceTest {
    @InjectMocks
    private LikeService likeService;

    @Mock
    private LikeRepository likeRepository;

    @Mock
    private AuthenticatedUserProvider authenticatedUserProvider;

    @Mock
    private PostRepository postRepository;

    private GramUser user;
    private GramPost post;
    private GramLike like;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = GramUser.builder()
                .id(2L)
                .email("user@test.com")
                .username("user")
                .role(new GramRole(RoleType.USER))
                .build();

        post = GramPost.builder()
                .id(1L)
                .title("Test Article")
                .content("test")
                .author(user)
                .build();

        like = GramLike.builder()
                .id(1L)
                .post(post)
                .user(user)
                .build();
    }

    @Test
    public void shouldLike(){
        when(authenticatedUserProvider.getAuthenticatedUser()).thenReturn(user);
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(likeRepository.save(any(GramLike.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        likeService.like(1L);

        verify(likeRepository, times(1)).save(any(GramLike.class));
    }

    @Test
    public void shouldRemoveLike(){
        when(authenticatedUserProvider.getAuthenticatedUser()).thenReturn(user);
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(likeRepository.findByUserAndPost(user, post)).thenReturn(Optional.of(like));

        likeService.removeLike(1L);

        verify(likeRepository, times(1)).delete(like);
    }
}