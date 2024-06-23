package com.udemy.martigram.controller;

import com.udemy.martigram.entity.GramFollow;
import com.udemy.martigram.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/follow")
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;

    @PostMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void follow(@PathVariable("id") long id){
        followService.followUser(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void unfollow(@PathVariable("id") long id){
        followService.unfollowUser(id);
    }
}
