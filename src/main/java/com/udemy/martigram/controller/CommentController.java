package com.udemy.martigram.controller;

import com.udemy.martigram.dto.CommentDTO;
import com.udemy.martigram.dto.CreateComment;
import com.udemy.martigram.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping
    public List<CommentDTO> view(){
        return commentService.findAll();
    }

    @GetMapping("/{id}")
    public CommentDTO view(@PathVariable("id") long id){
        return commentService.findById(id);
    }

    @PostMapping
    public CommentDTO create(@RequestBody CreateComment comment){
        return commentService.save(comment);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") long id){
        commentService.delete(id);
    }
}
