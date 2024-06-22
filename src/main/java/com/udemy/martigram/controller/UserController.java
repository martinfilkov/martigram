package com.udemy.martigram.controller;

import com.udemy.martigram.dto.UserDTO;
import com.udemy.martigram.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDTO> view(){
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public UserDTO view(@PathVariable long id){
        return userService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id){
        userService.delete(id);
    }

    @GetMapping("/profile")
    public UserDTO profile(){
        return userService.profile();
    }
}
