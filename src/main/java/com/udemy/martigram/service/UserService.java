package com.udemy.martigram.service;

import com.udemy.martigram.entity.GramUser;
import com.udemy.martigram.model.UserModel;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserModel> findAll();
    UserModel findById(long id);
    void delete(Long id);
    UserModel profile();
}
