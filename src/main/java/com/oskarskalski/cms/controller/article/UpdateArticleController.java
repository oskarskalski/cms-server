package com.oskarskalski.cms.controller.article;

import com.oskarskalski.cms.dto.ArticleDto;
import com.oskarskalski.cms.service.article.UpdateArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/article")
public class UpdateArticleController {
    private final UpdateArticleService updateArticleService;

    @Autowired
    public UpdateArticleController(UpdateArticleService updateArticleService) {
        this.updateArticleService = updateArticleService;
    }

    @PutMapping("/update/{id}")
    public void updateAer(
            @RequestBody ArticleDto articleDto,
            @RequestHeader(value = "Authorization", defaultValue = "") String header) {
        updateArticleService.updateByObjectAndAuthorizationHeader(articleDto, header);
    }
}
