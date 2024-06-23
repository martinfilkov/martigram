package com.udemy.martigram.mapper;

import com.udemy.martigram.dao.LikeRepository;
import com.udemy.martigram.dto.PostDTO;
import com.udemy.martigram.entity.GramPost;
import com.udemy.martigram.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class PostDTOMapper implements Function<GramPost, PostDTO> {
    private final LikeRepository likeRepository;

    @Override
    public PostDTO apply(GramPost post) {
        int likes = likeRepository.countByPost(post);

        return PostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .date(post.getDate())
                .likes(likes)
                .author_id(post.getAuthor().getId())
                .build();
    }
}
