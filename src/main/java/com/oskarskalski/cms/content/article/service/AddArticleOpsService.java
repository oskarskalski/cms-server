package com.oskarskalski.cms.content.article.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.oskarskalski.cms.content.article.model.dto.ArticleDto;
import com.oskarskalski.cms.global.exception.AccessDeniedException;
import com.oskarskalski.cms.global.exception.InvalidDataException;
import com.oskarskalski.cms.global.configuration.JwtConfiguration;
import com.oskarskalski.cms.content.article.model.Article;
import com.oskarskalski.cms.content.article.repo.ArticleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class AddArticleOpsService{
    private final ArticleRepo articleRepo;
    private final JwtConfiguration jwtConfiguration = new JwtConfiguration();

    public AddArticleOpsService(ArticleRepo articleRepo) {
        this.articleRepo = articleRepo;
    }

    public void addByObjectAndAuthorizationHeader(ArticleDto articleDto, String header) {
        if (articleDto.getTitle() == null || articleDto.getTitle().length() < 5 ||
                articleDto.getContent() == null || articleDto.getContent().length() < 30) {
            throw new InvalidDataException();
        }

        DecodedJWT decodedJWT = jwtConfiguration.parse(header);
        long userId = Long.parseLong(decodedJWT.getClaim("id").asString());

        if (userId > 0) {

            String generateArticleId = UUID.randomUUID().toString();
            Date date = new Date();

            Article article = new Article();

            article.setId(generateArticleId);
            article.setAuthorId(userId);
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

}
