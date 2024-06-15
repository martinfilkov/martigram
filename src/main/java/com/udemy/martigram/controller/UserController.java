package com.udemy.martigram.controller;

import com.udemy.martigram.entity.GramUser;
import com.udemy.martigram.exception.NotFoundException;
import com.udemy.martigram.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<GramUser> view(){
        return userService.findAll();
    }

    @GetMapping("/users/{id}")
    public Optional<GramUser> view(@PathVariable long id){
        Optional<GramUser> user = userService.findById(id);

        if(user.isPresent()) return user;
        else throw new NotFoundException("User with id " + id + " not found");
    }

    @PostMapping("/users")
    public GramUser create(@RequestBody GramUser user){
        return userService.save(user);
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id){
        Optional<GramUser> user = userService.findById(id);

        if(user.isEmpty()) throw new NotFoundException("User with id " + id + " not found");

        userService.delete(user.get());
    }
}
