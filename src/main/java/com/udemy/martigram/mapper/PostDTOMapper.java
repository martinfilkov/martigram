package com.udemy.martigram.mapper;

import com.udemy.martigram.dao.CommentRepository;
import com.udemy.martigram.dao.LikeRepository;
import com.udemy.martigram.dto.CommentDTO;
import com.udemy.martigram.dto.PostDTO;
import com.udemy.martigram.entity.GramPost;
import com.udemy.martigram.exception.InvalidActionException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class PostDTOMapper implements Function<GramPost, PostDTO> {
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
    private final CommentDTOMapper commentMapper;

    @Override
    public PostDTO apply(GramPost post) {
        if(post == null) throw new InvalidActionException("Post cannot be null");
        int likes = likeRepository.countByPost(post);
        List<CommentDTO> comments = commentRepository.findByPost(post).stream()
                .map(commentMapper)
                .toList();

        return PostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .date(post.getDate())
                .likes(likes)
                .authorId(post.getAuthor().getId())
                .comments(comments)
                .build();
    }
}
