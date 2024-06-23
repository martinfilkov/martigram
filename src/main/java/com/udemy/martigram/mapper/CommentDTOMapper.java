package com.udemy.martigram.mapper;

import com.udemy.martigram.dto.CommentDTO;
import com.udemy.martigram.entity.GramComment;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CommentDTOMapper implements Function<GramComment, CommentDTO> {
    @Override
    public CommentDTO apply(GramComment gramComment) {
        return CommentDTO.builder()
                .id(gramComment.getId())
                .content(gramComment.getContent())
                .userId(gramComment.getAuthor().getId())
                .build();
    }
}
