package com.oskarskalski.cms.service.article;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.oskarskalski.cms.crud.operation.SecuredAdd;
import com.oskarskalski.cms.dto.ArticleDto;
import com.oskarskalski.cms.exception.AccessDeniedException;
import com.oskarskalski.cms.exception.InvalidDataException;
import com.oskarskalski.cms.json.JwtConfiguration;
import com.oskarskalski.cms.model.Article;
import com.oskarskalski.cms.repo.ArticleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class AddArticleService implements SecuredAdd<ArticleDto> {
    private final ArticleRepo articleRepo;
    private final JwtConfiguration jwtConfiguration = new JwtConfiguration();

    @Autowired
    public AddArticleService(ArticleRepo articleRepo) {
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
