package com.udemy.martigram.service;

import com.udemy.martigram.dao.UserRepository;
import com.udemy.martigram.entity.GramUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<GramUser> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<GramUser> findById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public GramUser save(GramUser user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(GramUser user) {
        userRepository.delete(user);
    }
}
