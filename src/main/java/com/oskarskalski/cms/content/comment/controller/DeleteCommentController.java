package com.oskarskalski.cms.content.comment.controller;

import com.oskarskalski.cms.content.comment.service.DeleteCommentOpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment/")
public class DeleteCommentController {
    private final DeleteCommentOpsService deleteCommentService;

    @Autowired
    public DeleteCommentController(DeleteCommentOpsService deleteCommentService) {
        this.deleteCommentService = deleteCommentService;
    }

    @DeleteMapping("delete/{id}")
    public void softDeleteComment(
            @PathVariable long id,
            @RequestHeader(value = "Authorization", defaultValue = "") String header){
        deleteCommentService.softDeleteByIdAndAuthorizationHeader(id, header);
    }

    @DeleteMapping("delete/hard/{id}")
    public void hardDeleteComment(
            @PathVariable long id,
            @RequestHeader(value = "Authorization", defaultValue = "") String header){
        deleteCommentService.hardDeleteByIdAndAuthorizationHeader(id, header);
    }
}
