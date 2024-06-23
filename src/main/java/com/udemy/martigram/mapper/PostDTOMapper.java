package com.udemy.martigram.mapper;

import com.udemy.martigram.dto.PostDTO;
import com.udemy.martigram.entity.GramPost;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class PostDTOMapper implements Function<GramPost, PostDTO> {
    @Override
    public PostDTO apply(GramPost post) {
        return PostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .date(post.getDate())
                .author_id(post.getAuthor().getId())
                .build();
    }
}
