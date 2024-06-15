package com.udemy.martigram.service;

import com.udemy.martigram.entity.GramUser;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<GramUser> findAll();
    Optional<GramUser> findById(long id);
    GramUser save(GramUser user);
    void delete(GramUser user);
}
