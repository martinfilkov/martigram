package com.udemy.martigram.service;

import com.udemy.martigram.dao.CommentRepository;
import com.udemy.martigram.dao.PostRepository;
import com.udemy.martigram.dto.CommentDTO;
import com.udemy.martigram.dto.CreateComment;
import com.udemy.martigram.entity.GramComment;
import com.udemy.martigram.entity.GramPost;
import com.udemy.martigram.entity.GramUser;
import com.udemy.martigram.entity.RoleType;
import com.udemy.martigram.exception.NotFoundException;
import com.udemy.martigram.mapper.CommentDTOMapper;
import com.udemy.martigram.security.AuthenticatedUserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final CommentDTOMapper commentMapper;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    public List<CommentDTO> findAll(){
        List<GramComment> comments = commentRepository.findAll();

        return comments.stream()
                .map(commentMapper)
                .collect(Collectors.toList());
    }

    public CommentDTO findById(long id){
        Optional<GramComment> comment = commentRepository.findById(id);

        if(comment.isEmpty())
            throw new NotFoundException("Comment with id " + id + " not found");

        return commentMapper.apply(comment.get());
    }

    public CommentDTO save(@RequestBody CreateComment createComment){
        GramUser user = authenticatedUserProvider.getAuthenticatedUser();

        Optional<GramPost> post = postRepository.findById(createComment.getPostId());

        if(post.isEmpty())
            throw new NotFoundException("Post with id " + createComment.getPostId() + " not found");

        GramComment comment = GramComment.builder()
                .author(user)
                .post(post.get())
                .content(createComment.getContent())
                .build();

        commentRepository.save(comment);

        return commentMapper.apply(comment);
    }

    public void delete(long id){
        GramUser user = authenticatedUserProvider.getAuthenticatedUser();
        Optional<GramComment> comment = user.getRole().getRole() == RoleType.ADMIN
                ? commentRepository.findById(id)
                : commentRepository.findByIdAndAuthor(id, user);

        if(comment.isEmpty()) throw new NotFoundException("Post with id " + id + " not found");

        commentRepository.delete(comment.get());
    }
}
