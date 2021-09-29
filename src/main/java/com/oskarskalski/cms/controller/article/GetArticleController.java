package com.oskarskalski.cms.controller.article;

import com.oskarskalski.cms.dto.ArticleDto;
import com.oskarskalski.cms.model.Article;
import com.oskarskalski.cms.service.article.GetArticleOpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/article")
public class GetArticleController {
    private final GetArticleOpsService getArticleService;

    @Autowired
    public GetArticleController(GetArticleOpsService getArticleService) {
        this.getArticleService = getArticleService;
    }

    @GetMapping("/all/author/{authorId}")
    public List<ArticleDto> getArticlesByAuthorId(@PathVariable long authorId){
        return getArticleService.getAllArticlesByAuthorId(authorId);
    }

    @GetMapping("/{id}")
    public ArticleDto getArticleById(@PathVariable String id){
        return getArticleService.getById(id);
    }

    @GetMapping("/all/team/{teamId}")
    public List<ArticleDto> getArticlesByTeamId(@PathVariable String teamId){
        return getArticleService.getAllArticlesByTeamId(teamId);
    }
    @GetMapping("/all")
    public List<ArticleDto> getArticlesByHeader(@RequestHeader("Authorization")String header){
        return getArticleService.getAllArticlesBy(header);
    }
}
