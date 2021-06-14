package com.oskarskalski.cms.service;

import com.oskarskalski.cms.dto.ArticleDto;
import com.oskarskalski.cms.model.Article;
import com.oskarskalski.cms.repo.ArticleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ArticleService {
    private final ArticleRepo articleRepo;

    @Autowired
    public ArticleService(ArticleRepo articleRepo) {
        this.articleRepo = articleRepo;
    }

    public ArticleDto getArticleByAuthorId(long authorId) {
        ArticleDto articleDto = articleRepo.findByAuthorId(authorId)
                .orElseThrow(NullPointerException::new)
                .convertToDto();

        return articleDto;
    }


    public List<ArticleDto> getAllArticlesByAuthorId(long authorId) {
        List<Article> articles = articleRepo.findAllByAuthorId(authorId)
                .orElseThrow(NullPointerException::new);

        List<ArticleDto> articlesDto = new ArrayList<>();

        for(Article article: articles){
            articlesDto.add(article.convertToDto());
        }

        return articlesDto;
    }

    public void addArticle(ArticleDto articleDto, long authorId) {
        String generateArticleId = UUID.randomUUID().toString();
        Date date = new Date();

        Article article = new Article();

        article.setId(generateArticleId);
        article.setAuthorId(authorId);
        article.setTitle(articleDto.getTitle());
        article.setContent(articleDto.getContent());
        article.setDate(date);
        article.setSoftDelete(false);

        if (articleDto.getImages() != null) {
            StringBuilder stringBuilder = new StringBuilder();

            for (String i : articleDto.getImages()) {
                stringBuilder
                        .append(i)
                        .append(" ");
            }
            article.setNamesOfImages(stringBuilder.toString());

        }
        articleRepo.save(article);
    }

    public void softDeleteArticleByArticleIdAndAuthorId(String articleId, long authorId) {
        Article article = articleRepo.findById(articleId)
                .orElseThrow(NullPointerException::new);

        if (article.getAuthorId() == authorId) {
            article.setSoftDelete(true);
            articleRepo.save(article);
        }
    }

    public void deleteArticleByArticleIdAndAuthorId(String articleId, long authorId) {
        Article article = articleRepo.findById(articleId)
                .orElseThrow(NullPointerException::new);

        if (authorId == article.getAuthorId()) {
            articleRepo.deleteById(articleId);
        }
    }
}
