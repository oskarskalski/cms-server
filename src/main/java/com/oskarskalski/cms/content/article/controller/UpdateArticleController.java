package com.oskarskalski.cms.content.article.controller;

import com.oskarskalski.cms.content.article.model.dto.ArticleDto;
import com.oskarskalski.cms.content.article.service.UpdateArticleOpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/article")
public class UpdateArticleController {
    private final UpdateArticleOpsService updateArticleService;

    @Autowired
    public UpdateArticleController(UpdateArticleOpsService updateArticleService) {
        this.updateArticleService = updateArticleService;
    }

    @PutMapping("/update")
    public void updateAer(
            @RequestBody ArticleDto articleDto,
            @RequestHeader(value = "Authorization", defaultValue = "") String header) {
        updateArticleService.updateByObjectAndAuthorizationHeader(articleDto, header);
    }
}
