package com.oskarskalski.cms.controller;

import com.oskarskalski.cms.dto.CommentDto;
import com.oskarskalski.cms.model.Comment;
import com.oskarskalski.cms.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment/")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("{id}")
    public CommentDto getCommentById(@PathVariable long id){
        return commentService.getCommentById(id);
    }

    @PostMapping("add")
    public void addComment(@RequestBody Comment comment, long authorId){
        commentService.addComment(comment, authorId);
    }

    @DeleteMapping("delete/{id}")
    public void deleteComment(@PathVariable long id, long authorId){
        commentService.softDeleteComment(id, authorId);
    }
}
