package com.oskarskalski.cms.content.article.controller;

import com.oskarskalski.cms.content.article.service.DeleteArticleOpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/article/")
public class DeleteArticleController {
    private final DeleteArticleOpsService deleteArticleService;

    @Autowired
    public DeleteArticleController(DeleteArticleOpsService deleteArticleService) {
        this.deleteArticleService = deleteArticleService;
    }

    @DeleteMapping("soft/{id}")
    public void softDeleteById(@PathVariable String id,
                               @RequestHeader(value = "Authorization", defaultValue = "") String header){

        deleteArticleService.softDeleteByIdAndAuthorizationHeader(id, header);
    }

    @DeleteMapping("hard/{id}")
    public void hardDeleteById(@PathVariable String id,
                               @RequestHeader(value = "Authorization", defaultValue = "") String header){

        deleteArticleService.hardDeleteByIdAndAuthorizationHeader(id, header);
    }
}
