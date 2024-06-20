package com.udemy.martigram.controller;

import com.udemy.martigram.entity.GramUser;
import com.udemy.martigram.exception.NotFoundException;
import com.udemy.martigram.model.UserModel;
import com.udemy.martigram.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserModel> view(){
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public UserModel view(@PathVariable long id){
        return userService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id){
        userService.delete(id);
    }

    @GetMapping("/profile")
    public UserModel profile(){
        return userService.profile();
    }
}
