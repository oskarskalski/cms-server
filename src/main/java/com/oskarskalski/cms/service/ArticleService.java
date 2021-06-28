package com.oskarskalski.cms.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.oskarskalski.cms.dto.ArticleDto;
import com.oskarskalski.cms.exception.AccessDeniedException;
import com.oskarskalski.cms.exception.InvalidDataException;
import com.oskarskalski.cms.exception.NotFoundException;
import com.oskarskalski.cms.json.JwtConfiguration;
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
    private final JwtConfiguration jwtConfiguration = new JwtConfiguration();

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

        for (Article article : articles) {
            articlesDto.add(article.convertToDto());
        }

        return articlesDto;
    }

    public void addArticle(ArticleDto articleDto, String header) {
        if (articleDto.getTitle() == null || articleDto.getTitle().length() < 5 ||
                articleDto.getContent() == null || articleDto.getContent().length() < 30) {
            throw new InvalidDataException();
        }

        if (header == null || !header.startsWith("Bearer ")) {
            throw new AccessDeniedException();
        }
        DecodedJWT decodedJWT = jwtConfiguration.parse(header);
        long user_id = Long.parseLong(decodedJWT.getClaim("id").asString());

        if (user_id > 0) {

            String generateArticleId = UUID.randomUUID().toString();
            Date date = new Date();

            Article article = new Article();

            article.setId(generateArticleId);
            article.setAuthorId(user_id);
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
        } else
            throw new AccessDeniedException();
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

    public void updateArticle(ArticleDto articleDto, String header, String id) {
        if(header == null || !header.startsWith("Bearer")){
            throw new AccessDeniedException();
        }

        if(articleDto.getContent() == null && articleDto.getTitle() == null) {
            throw new InvalidDataException();
        }

        if(articleDto.getTitle() != null &&
                (articleDto.getTitle().length() < 5 || articleDto.getTitle().length() > 30)){
            throw new InvalidDataException();
        }

        if(articleDto.getContent() != null &&
                (articleDto.getContent().length() < 50 || articleDto.getContent().length() > 2000)){
            throw new InvalidDataException();
        }

        Article article = articleRepo.findById(id)
                .orElseThrow(NotFoundException::new);

        if(articleDto.getTitle() != null && !article.getTitle().equals(articleDto.getTitle())){
            article.setTitle(articleDto.getTitle());
        }
        if(articleDto.getContent() != null && !article.getContent().equals(articleDto.getContent())){
            article.setContent(articleDto.getContent());
        }

        articleRepo.save(article);
    }
}
