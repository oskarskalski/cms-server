package com.oskarskalski.cms.controller;

import com.oskarskalski.cms.dto.ArticleDto;
import com.oskarskalski.cms.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/article/")
public class ArticleController {
    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/add")
    public void addArticle(@RequestBody ArticleDto articleDto,
                           @RequestHeader(value = "Authorization", defaultValue = "") String header) {
        articleService.addArticle(articleDto, header);
    }

    @PutMapping("/update/{id}")
    public void updateArticle(@RequestBody ArticleDto articleDto,
                              @RequestHeader(value = "Authorization", defaultValue = "") String header,
                              @PathVariable String id){
        articleService.updateArticle(articleDto, header, id);
    }

    @GetMapping("{authorId}")
    public ArticleDto getArticleByAuthorId(@PathVariable("authorId") long authorId) {
        return articleService.getArticleByAuthorId(authorId);
    }

    @GetMapping("{authorId}/all")
    public List<ArticleDto> getAllArticlesByAuthorId(@PathVariable("authorId") long authorId) {
        return articleService.getAllArticlesByAuthorId(authorId);
    }

    @DeleteMapping("delete/soft/{id}")
    public void softDeleteArticle(@PathVariable String id, long authorId) {
        articleService.softDeleteArticleByArticleIdAndAuthorId(id, authorId);
    }

    @DeleteMapping("delete/hard/{id}")
    public void hardDeleteArticle(@PathVariable String id, long authorId) {
        articleService.softDeleteArticleByArticleIdAndAuthorId(id, authorId);
    }
}
