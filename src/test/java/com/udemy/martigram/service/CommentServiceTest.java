package com.udemy.martigram.service;

import com.udemy.martigram.dao.CommentRepository;
import com.udemy.martigram.dao.PostRepository;
import com.udemy.martigram.dto.CommentDTO;
import com.udemy.martigram.dto.CreateComment;
import com.udemy.martigram.entity.*;
import com.udemy.martigram.exception.NotFoundException;
import com.udemy.martigram.mapper.CommentDTOMapper;
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

class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private CommentDTOMapper commentMapper;

    @Mock
    private AuthenticatedUserProvider authenticatedUserProvider;

    private GramUser adminUser;
    private GramUser user;
    private GramPost post;
    private GramComment comment;
    private CommentDTO dto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        adminUser = GramUser.builder()
                .id(1L)
                .email("admin@test.com")
                .username("admin")
                .role(new GramRole(RoleType.ADMIN))
                .build();

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

        comment = GramComment.builder()
                .id(1L)
                .author(user)
                .post(post)
                .content("Test Comment")
                .build();

        dto = new CommentDTO(1L, "Test Comment", user.getId());
    }

    @Test
    public void shouldSaveComment(){
        CreateComment createComment = new CreateComment(1L,"Test Comment");

        when(authenticatedUserProvider.getAuthenticatedUser()).thenReturn(user);
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(commentRepository.save(any(GramComment.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(commentMapper.apply(any(GramComment.class))).thenReturn(dto);

        CommentDTO result = commentService.save(createComment);

        assertNotNull(result);
        assertEquals(dto.getContent(), result.getContent());
        assertEquals(dto.getAuthorId(), result.getAuthorId());
    }

    @Test
    public void shouldReturnAllComments(){
        when(authenticatedUserProvider.getAuthenticatedUser()).thenReturn(user);
        when(commentRepository.findAll()).thenReturn(List.of(comment));
        when(commentMapper.apply(any(GramComment.class))).thenReturn(dto);

        List<CommentDTO> result = commentService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void shouldReturnCommentById(){
        when(authenticatedUserProvider.getAuthenticatedUser()).thenReturn(user);
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));
        when(commentMapper.apply(any(GramComment.class))).thenReturn(dto);

        CommentDTO result = commentService.findById(1L);

        assertNotNull(result);
        assertEquals(dto.getContent(), result.getContent());
        assertEquals(dto.getAuthorId(), result.getAuthorId());
    }

    @Test
    public void shouldDeleteCommentByIdForAdmin(){
        when(authenticatedUserProvider.getAuthenticatedUser()).thenReturn(adminUser);
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));

        commentService.delete(1L);

        verify(commentRepository, times(1)).delete(comment);
    }

    @Test
    public void shouldDeleteCommentByIdForUser(){
        when(authenticatedUserProvider.getAuthenticatedUser()).thenReturn(user);
        when(postRepository.findByIdAndAuthor(1L, user)).thenReturn(Optional.of(post));
        when(commentRepository.findByIdAndAuthor(1L, user)).thenReturn(Optional.of(comment));

        commentService.delete(1L);

        verify(commentRepository, times(1)).delete(comment);
    }

    @Test
    public void shouldThrowExceptionWhenCommentNotFoundForDeletion() {
        when(authenticatedUserProvider.getAuthenticatedUser()).thenReturn(adminUser);
        when(commentRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            commentService.delete(1L);
        });

        assertEquals("Comment with id 1 not found", thrown.getMessage());
    }
}