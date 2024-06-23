package com.udemy.martigram.mapper;

import com.udemy.martigram.dao.FollowerRepository;
import com.udemy.martigram.dao.PostRepository;
import com.udemy.martigram.dto.PostDTO;
import com.udemy.martigram.dto.UserDTO;
import com.udemy.martigram.entity.GramUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDTOMapper implements Function<GramUser, UserDTO> {
    private final FollowerRepository followerRepository;
    private final PostRepository postRepository;
    private final PostDTOMapper postMapper;
    @Override
    public UserDTO apply(GramUser user) {
        int followerCount = followerRepository.countByFollowed(user);
        int followingCount = followerRepository.countByFollower(user);
        List<PostDTO> posts = postRepository.findByAuthor(user).stream()
                .map(postMapper)
                .collect(Collectors.toList());


        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role_id(user.getRole().getId())
                .followerCount(followerCount)
                .followingCount(followingCount)
                .posts(posts)
                .build();
    }
}
