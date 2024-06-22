package com.udemy.martigram.controller;

import com.udemy.martigram.dto.CreatePost;
import com.udemy.martigram.dto.PostDTO;
import com.udemy.martigram.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {
    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<PostDTO> view(){
        return postService.findAll();
    }

    @GetMapping("/{id}")
    public PostDTO view(@PathVariable("id") long id){
        return postService.findById(id);
    }

    @PostMapping
    public PostDTO create(@RequestBody CreatePost createPost){
        return postService.save(createPost);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") long id){
        postService.delete(id);
    }
}
