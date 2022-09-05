package com.oskarskalski.cms.content.article.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.oskarskalski.cms.crud.operation.SecuredDelete;
import com.oskarskalski.cms.global.configuration.JwtConfiguration;
import com.oskarskalski.cms.content.article.model.Article;
import com.oskarskalski.cms.content.article.repo.ArticleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteArticleOpsService implements SecuredDelete<String> {
    private final ArticleRepo articleRepo;
    private final JwtConfiguration jwtConfiguration = new JwtConfiguration();

    @Autowired
    public DeleteArticleOpsService(ArticleRepo articleRepo) {
        this.articleRepo = articleRepo;
    }

    public void softDeleteByIdAndAuthorizationHeader(String articleId, String header) {
        Article article = articleRepo.findById(articleId)
                .orElseThrow(NullPointerException::new);

        DecodedJWT decodedJWT = jwtConfiguration.parse(header);
        long userId = Long.parseLong(decodedJWT.getClaim("id").asString());

        if(userId == article.getAuthorId()){
            article.setSoftDelete(!article.isSoftDelete());
            articleRepo.save(article);
        }
    }

    public void hardDeleteByIdAndAuthorizationHeader(String articleId, String header) {
        Article article = articleRepo.findById(articleId)
                .orElseThrow(NullPointerException::new);

        DecodedJWT decodedJWT = jwtConfiguration.parse(header);
        long userId = Long.parseLong(decodedJWT.getClaim("id").asString());

        if(userId == article.getAuthorId()){
            articleRepo.deleteById(articleId);
        }
    }
}
