package com.oskarskalski.cms.controller.article;

import com.oskarskalski.cms.dto.ArticleDto;
import com.oskarskalski.cms.service.article.AddArticleOpsService;
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
