package com.udemy.martigram.service;

import com.udemy.martigram.dto.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> findAll();
    UserDTO findById(long id);
    void delete(Long id);
    UserDTO profile();
}
