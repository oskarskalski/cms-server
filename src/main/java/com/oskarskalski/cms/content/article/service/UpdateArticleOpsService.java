package com.oskarskalski.cms.content.article.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.oskarskalski.cms.crud.operation.SecuredUpdate;
import com.oskarskalski.cms.content.article.model.dto.ArticleDto;
import com.oskarskalski.cms.global.exception.AccessDeniedException;
import com.oskarskalski.cms.global.exception.InvalidDataException;
import com.oskarskalski.cms.global.exception.NotFoundException;
import com.oskarskalski.cms.global.configuration.JwtConfiguration;
import com.oskarskalski.cms.content.article.model.Article;
import com.oskarskalski.cms.content.article.repo.ArticleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateArticleOpsService implements SecuredUpdate<ArticleDto> {
    private final ArticleRepo articleRepo;
    private final JwtConfiguration jwtConfiguration = new JwtConfiguration();

    @Autowired
    public UpdateArticleOpsService(ArticleRepo articleRepo) {
        this.articleRepo = articleRepo;
    }

    public void updateByObjectAndAuthorizationHeader(ArticleDto articleDto, String header) {
        DecodedJWT decodedJWT = jwtConfiguration.parse(header);
        long userId = Long.parseLong(decodedJWT.getClaim("id").asString());

        if (articleDto.getContent() == null && articleDto.getTitle() == null) {
            throw new InvalidDataException();
        }

        if (articleDto.getTitle() != null &&
                (articleDto.getTitle().length() < 5 || articleDto.getTitle().length() > 30)) {
            throw new InvalidDataException();
        }

        if (articleDto.getContent() != null &&
                (articleDto.getContent().length() < 50 || articleDto.getContent().length() > 2000)) {
            throw new InvalidDataException();
        }

        Article article = articleRepo.findById(articleDto.getId())
                .orElseThrow(NotFoundException::new);

        if(article.getAuthorId() != userId){
            throw new AccessDeniedException();
        }

        if (articleDto.getTitle() != null && !article.getTitle().equals(articleDto.getTitle())) {
            article.setTitle(articleDto.getTitle());
        }
        if (articleDto.getContent() != null && !article.getContent().equals(articleDto.getContent())) {
            article.setContent(articleDto.getContent());
        }

        articleRepo.save(article);
    }
}
