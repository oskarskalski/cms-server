package com.oskarskalski.cms.controller.comment;

import com.oskarskalski.cms.dto.CommentDto;
import com.oskarskalski.cms.service.comment.AddCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment/")
public class AddCommentController {
    private final AddCommentService addCommentService;

    @Autowired
    public AddCommentController(AddCommentService addCommentService) {
        this.addCommentService = addCommentService;
    }

    @PostMapping("add/{articleId}")
    public void addComment(
            @RequestBody CommentDto commentDto,
            @RequestHeader(value = "Authorization", defaultValue = "") String header) {

        addCommentService.addByObjectAndAuthorizationHeader(commentDto, header);
    }
}
