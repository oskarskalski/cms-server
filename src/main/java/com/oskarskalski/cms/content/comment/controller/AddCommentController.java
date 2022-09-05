package com.oskarskalski.cms.content.comment.controller;

import com.oskarskalski.cms.content.comment.model.dto.CommentDto;
import com.oskarskalski.cms.content.comment.service.AddCommentOpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment/")
public class AddCommentController {
    private final AddCommentOpsService addCommentService;

    @Autowired
    public AddCommentController(AddCommentOpsService addCommentService) {
        this.addCommentService = addCommentService;
    }

    @PostMapping("add/{articleId}")
    public void addComment(
            @RequestBody CommentDto commentDto,
            @RequestHeader(value = "Authorization", defaultValue = "") String header) {

        addCommentService.addByObjectAndAuthorizationHeader(commentDto, header);
    }
}
