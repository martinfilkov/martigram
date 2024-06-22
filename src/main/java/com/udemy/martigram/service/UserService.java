package com.udemy.martigram.service;

import com.udemy.martigram.dao.UserRepository;
import com.udemy.martigram.entity.GramUser;
import com.udemy.martigram.exception.NotFoundException;
import com.udemy.martigram.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService{
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> findAll() {
        List<GramUser> users = userRepository.findAll();
        return users.stream()
                .map(this::buildUser)
                .collect(Collectors.toList());
    }

    public UserDTO findById(long id) {
        Optional<GramUser> findUser = userRepository.findById(id);

        if(findUser.isPresent()) {
            return buildUser(findUser.get());
        }
        else throw new NotFoundException("User with id " + id + " not found");
    }

    public void delete(Long id) {
        Optional<GramUser> deleteUser = userRepository.findById(id);

        if(deleteUser.isEmpty()) throw new NotFoundException("User with id " + id + " not found");

        userRepository.delete(deleteUser.get());
    }

    public UserDTO profile() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Optional<GramUser> findUser = userRepository.findByUsername(auth.getName());
        if(findUser.isPresent()) return buildUser(findUser.get());
        else throw new NotFoundException("User authentication not found");
    }

    private UserDTO buildUser(GramUser user){
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role_id(user.getRole().getId())
                .build();
    }
}
