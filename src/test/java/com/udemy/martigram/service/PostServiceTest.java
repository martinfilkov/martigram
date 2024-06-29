package com.udemy.martigram.service;

import com.udemy.martigram.dao.PostRepository;
import com.udemy.martigram.dto.CreatePost;
import com.udemy.martigram.dto.PostDTO;
import com.udemy.martigram.entity.GramPost;
import com.udemy.martigram.entity.GramRole;
import com.udemy.martigram.entity.GramUser;
import com.udemy.martigram.entity.RoleType;
import com.udemy.martigram.exception.NotFoundException;
import com.udemy.martigram.mapper.PostDTOMapper;
import com.udemy.martigram.security.AuthenticatedUserProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PostServiceTest {
    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private AuthenticatedUserProvider authenticatedUserProvider;

    @Mock
    private PostDTOMapper postMapper;

    private GramUser adminUser;
    private GramUser regularUser;
    private GramPost post;
    private PostDTO dto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        adminUser = GramUser.builder()
                .id(1L)
                .email("admin@test.com")
                .username("admin")
                .role(new GramRole(RoleType.ADMIN))
                .build();

        regularUser = GramUser.builder()
                .id(2L)
                .email("user@test.com")
                .username("user")
                .role(new GramRole(RoleType.USER))
                .build();

        post = GramPost.builder()
                .id(1L)
                .title("Test Article")
                .content("test")
                .author(regularUser)
                .build();

        dto = PostDTO.builder()
                .id(1L)
                .title("Test Article")
                .authorId(regularUser.getId())
                .content("test")
                .build();
    }

    @Test
    public void shouldSavePost(){
        CreatePost createPost = new CreatePost("Test Article", "test");

        when(authenticatedUserProvider.getAuthenticatedUser()).thenReturn(regularUser);
        when(postRepository.save(any(GramPost.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(postMapper.apply(any(GramPost.class))).thenReturn(dto);

        PostDTO result = postService.save(createPost);

        assertNotNull(result);
        assertEquals(dto.getTitle(), result.getTitle());
        assertEquals(dto.getContent(), result.getContent());
        assertEquals(dto.getAuthorId(), result.getAuthorId());
    }

    @Test
    public void shouldReturnAllPostsForAdmin(){
        when(authenticatedUserProvider.getAuthenticatedUser()).thenReturn(adminUser);
        when(postRepository.findAll()).thenReturn(List.of(post));
        when(postMapper.apply(any(GramPost.class))).thenReturn(dto);

        List<PostDTO> result = postService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void shouldReturnAllPostsForUser(){
        when(authenticatedUserProvider.getAuthenticatedUser()).thenReturn(regularUser);
        when(postRepository.findByAuthor(any(GramUser.class))).thenReturn(List.of(post));
        when(postMapper.apply(any(GramPost.class))).thenReturn(dto);

        List<PostDTO> result = postService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void shouldReturnPostByIdForAdmin(){
        when(authenticatedUserProvider.getAuthenticatedUser()).thenReturn(adminUser);
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(postMapper.apply(any(GramPost.class))).thenReturn(dto);

        PostDTO result = postService.findById(1L);

        assertNotNull(result);
        assertEquals(result.getContent(), post.getContent());
        assertEquals(result.getTitle(), post.getTitle());
        assertEquals(result.getAuthorId(), post.getAuthor().getId());
    }

    @Test
    public void shouldReturnPostByIdForUser(){
        when(authenticatedUserProvider.getAuthenticatedUser()).thenReturn(regularUser);
        when(postRepository.findByIdAndAuthor(1L, regularUser)).thenReturn(Optional.of(post));
        when(postMapper.apply(any(GramPost.class))).thenReturn(dto);

        PostDTO result = postService.findById(1L);

        assertNotNull(result);
        assertEquals(result.getContent(), post.getContent());
        assertEquals(result.getTitle(), post.getTitle());
        assertEquals(result.getAuthorId(), post.getAuthor().getId());
    }

    @Test
    public void shouldDeletePostForAdmin(){
        when(authenticatedUserProvider.getAuthenticatedUser()).thenReturn(adminUser);
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        postService.delete(1L);

        verify(postRepository, times(1)).delete(post);
    }

    @Test
    public void shouldDeletePostForUser(){
        when(authenticatedUserProvider.getAuthenticatedUser()).thenReturn(regularUser);
        when(postRepository.findByIdAndAuthor(1L, regularUser)).thenReturn(Optional.of(post));

        postService.delete(1L);

        verify(postRepository, times(1)).delete(post);
    }

    @Test
    public void shouldThrowExceptionWhenPostNotFoundForDeletion() {
        when(authenticatedUserProvider.getAuthenticatedUser()).thenReturn(adminUser);
        when(postRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            postService.delete(1L);
        });

        assertEquals("Post with id 1 not found", thrown.getMessage());
    }
}