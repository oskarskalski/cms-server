package com.oskarskalski.cms.controller.article;

import com.oskarskalski.cms.dto.ArticleDto;
import com.oskarskalski.cms.service.article.AddArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/article/")
public class AddArticleController {
    private final AddArticleService addArticleService;

    @Autowired
    public AddArticleController(AddArticleService addArticleService) {
        this.addArticleService = addArticleService;
    }

    @PostMapping("add")
    public void addArticle(@RequestBody ArticleDto articleDto,
                           @RequestHeader(value = "Authorization", defaultValue = "") String header) {
        addArticleService.addByObjectAndAuthorizationHeader(articleDto, header);
    }
}
