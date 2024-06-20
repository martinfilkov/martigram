package com.udemy.martigram.service;

import com.udemy.martigram.dao.UserRepository;
import com.udemy.martigram.entity.GramUser;
import com.udemy.martigram.exception.NotFoundException;
import com.udemy.martigram.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserModel> findAll() {
        List<GramUser> users = userRepository.findAll();
        return users.stream()
                .map(this::buildUser)
                .collect(Collectors.toList());
    }

    @Override
    public UserModel findById(long id) {
        Optional<GramUser> findUser = userRepository.findById(id);

        if(findUser.isPresent()) {
            return buildUser(findUser.get());
        }
        else throw new NotFoundException("User with id " + id + " not found");
    }

    @Override
    public void delete(Long id) {
        Optional<GramUser> deleteUser = userRepository.findById(id);

        if(deleteUser.isEmpty()) throw new NotFoundException("User with id " + id + " not found");

        userRepository.delete(deleteUser.get());
    }

    @Override
    public UserModel profile() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Optional<GramUser> findUser = userRepository.findByUsername(auth.getName());
        if(findUser.isPresent()) return buildUser(findUser.get());
        else throw new NotFoundException("User authentication not found");
    }

    private UserModel buildUser(GramUser user){
        return UserModel.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role_id(user.getRole().getId())
                .build();
    }
}
