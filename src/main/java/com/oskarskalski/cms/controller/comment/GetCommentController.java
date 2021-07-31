package com.oskarskalski.cms.controller.comment;

import com.oskarskalski.cms.dto.CommentDto;
import com.oskarskalski.cms.service.comment.GetCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comment/")
public class GetCommentController {
    private final GetCommentService getCommentService;

    @Autowired
    public GetCommentController(GetCommentService getCommentService) {
        this.getCommentService = getCommentService;
    }

    @GetMapping("{commentId}")
    public CommentDto getComment(@PathVariable long commentId){
        return getCommentService.getById(commentId);
    }
}
