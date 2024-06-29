package com.udemy.martigram.mapper;

import com.udemy.martigram.dto.CommentDTO;
import com.udemy.martigram.entity.GramComment;
import com.udemy.martigram.entity.GramPost;
import com.udemy.martigram.entity.GramUser;
import com.udemy.martigram.exception.InvalidActionException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommentDTOMapperTest {

    @Autowired
    private CommentDTOMapper mapper;

    @Test
    public void shouldMapCommentToCommentDto(){
        GramUser user = GramUser.builder()
                .id(1L)
                .username("test")
                .email("test@test.com")
                .password("test")
                .build();

        GramPost post = GramPost.builder()
                .id(1L)
                .title("Test")
                .content("Content")
                .author(user)
                .build();

        GramComment comment = GramComment.builder()
                .content("test")
                .post(post)
                .author(user)
                .build();

        CommentDTO dto = mapper.apply(comment);

        assertEquals(dto.getContent(), comment.getContent());
        assertEquals(dto.getAuthorId(), comment.getAuthor().getId());
    }


    @Test
    public void shouldThrowInvalidArgumentsExceptionWhenCommentIsNull(){
        var ex = assertThrows(InvalidActionException.class, () -> mapper.apply(null));

        assertEquals("Comment cannot be null", ex.getMessage());
    }
}