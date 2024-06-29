package com.udemy.martigram.mapper;

import com.udemy.martigram.dto.PostDTO;
import com.udemy.martigram.entity.GramPost;
import com.udemy.martigram.entity.GramUser;
import com.udemy.martigram.exception.InvalidActionException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class PostDTOMapperTest {

    @Autowired
    private PostDTOMapper mapper;


    @Test
    public void shouldMapPostToPostDto(){
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

        PostDTO dto = mapper.apply(post);

        assertEquals(dto.getAuthorId(), post.getAuthor().getId());
        assertEquals(dto.getTitle(), post.getTitle());
        assertEquals(dto.getContent(), post.getContent());
    }

    @Test
    public void shouldThrowInvalidArgumentsExceptionWhenPostIsNull(){
        var ex = assertThrows(InvalidActionException.class, () -> mapper.apply(null));

        assertEquals("Post cannot be null", ex.getMessage());
    }
}