package com.oskarskalski.cms.content.article.controller;

import com.oskarskalski.cms.content.article.model.dto.ArticleDto;
import com.oskarskalski.cms.content.article.service.AddArticleOpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/article/")
public class AddArticleController {
    private final AddArticleOpsService addArticleService;

    @Autowired
    public AddArticleController(AddArticleOpsService addArticleService) {
        this.addArticleService = addArticleService;
    }

    @PostMapping("add")
    public void addArticle(@RequestBody ArticleDto articleDto,
                           @RequestHeader(value = "Authorization", defaultValue = "") String header) {
        addArticleService.addByObjectAndAuthorizationHeader(articleDto, header);
    }
}
