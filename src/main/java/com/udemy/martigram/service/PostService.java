package com.udemy.martigram.service;

import com.udemy.martigram.dao.PostRepository;
import com.udemy.martigram.dto.CreatePost;
import com.udemy.martigram.entity.GramPost;
import com.udemy.martigram.entity.GramUser;
import com.udemy.martigram.entity.RoleType;
import com.udemy.martigram.exception.NotFoundException;
import com.udemy.martigram.dto.PostDTO;
import com.udemy.martigram.mapper.PostDTOMapper;
import com.udemy.martigram.security.AuthenticatedUserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final AuthenticatedUserProvider authenticatedUserProvider;
    private final PostDTOMapper postMapper;

    public List<PostDTO> findAll() {
        GramUser user = authenticatedUserProvider.getAuthenticatedUser();
        List<GramPost> posts = user.getRole().getRole() == RoleType.ADMIN
                ? postRepository.findAll()
                : postRepository.findByAuthor(user);
        return posts.stream()
                .map(postMapper)
                .collect(Collectors.toList());
    }


    public PostDTO findById(long id) {
        GramUser user = authenticatedUserProvider.getAuthenticatedUser();
        Optional<GramPost> postOptional;

        if (user.getRole().getRole() == RoleType.ADMIN) {
            postOptional = postRepository.findById(id);
        } else {
            postOptional = postRepository.findByIdAndAuthor(id, user);
        }

        GramPost post = postOptional.orElseThrow(() -> new NotFoundException("Post with id " + id + " not found"));
        return postMapper.apply(post);
    }


    public PostDTO save(CreatePost createPost){
        GramUser user = authenticatedUserProvider.getAuthenticatedUser();

        GramPost post = GramPost.builder()
                .title(createPost.getTitle())
                .content(createPost.getContent())
                .date(new Timestamp(System.currentTimeMillis()))
                .author(user)
                .build();

        postRepository.save(post);

        return postMapper.apply(post);
    }

    public void delete(long id){
        GramUser user = authenticatedUserProvider.getAuthenticatedUser();
        Optional<GramPost> postOptional;

        if (user.getRole().getRole() == RoleType.ADMIN) {
            postOptional = postRepository.findById(id);
        } else {
            postOptional = postRepository.findByIdAndAuthor(id, user);
        }

        GramPost post = postOptional.orElseThrow(() -> new NotFoundException("Post with id " + id + " not found"));
        postRepository.delete(post);
    }
}
