package com.udemy.martigram.mapper;

import com.udemy.martigram.dto.CommentDTO;
import com.udemy.martigram.entity.GramComment;
import com.udemy.martigram.exception.InvalidActionException;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CommentDTOMapper implements Function<GramComment, CommentDTO> {
    @Override
    public CommentDTO apply(GramComment comment) {
        if(comment == null) throw new InvalidActionException("Comment cannot be null");
        return CommentDTO.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .authorId(comment.getAuthor().getId())
                .build();
    }
}
