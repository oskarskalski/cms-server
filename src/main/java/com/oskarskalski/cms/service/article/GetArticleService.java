package com.oskarskalski.cms.service.article;

import com.oskarskalski.cms.crud.operation.Get;
import com.oskarskalski.cms.dto.ArticleDto;
import com.oskarskalski.cms.model.Article;
import com.oskarskalski.cms.repo.ArticleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetArticleService implements Get<ArticleDto, String> {
    private final ArticleRepo articleRepo;

    @Autowired
    public GetArticleService(ArticleRepo articleRepo) {
        this.articleRepo = articleRepo;
    }

    public ArticleDto getById(String id) {
        ArticleDto articleDto = articleRepo.findById(id)
                .orElseThrow(NullPointerException::new)
                .convertToDto();

        return articleDto;
    }


    public List<ArticleDto> getAllArticlesByAuthorId(long authorId) {
        List<Article> articles = articleRepo.findAllByAuthorId(authorId)
                .orElseThrow(NullPointerException::new);

        List<ArticleDto> articlesDto = new ArrayList<>();

        for (Article article : articles) {
            articlesDto.add(article.convertToDto());
        }

        return articlesDto;
    }

    public List<ArticleDto> getArticlesByTeamId(String teamId) {
        List<Article> articles = articleRepo.findAllByTeamId(teamId)
                .orElseThrow(NullPointerException::new);

        List<ArticleDto> articlesDto = new ArrayList<>();

        for (Article article : articles) {
            articlesDto.add(article.convertToDto());
        }

        return articlesDto;
    }
}

