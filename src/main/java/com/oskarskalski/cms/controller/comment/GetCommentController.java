package com.oskarskalski.cms.controller.comment;

import com.oskarskalski.cms.dto.CommentDto;
import com.oskarskalski.cms.service.comment.GetCommentOpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comment/")
public class GetCommentController {
    private final GetCommentOpsService getCommentService;

    @Autowired
    public GetCommentController(GetCommentOpsService getCommentService) {
        this.getCommentService = getCommentService;
    }

    @GetMapping("{commentId}")
    public CommentDto getComment(@PathVariable long commentId){
        return getCommentService.getById(commentId);
    }
}
