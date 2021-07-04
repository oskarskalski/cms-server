package com.oskarskalski.cms.service.article;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.oskarskalski.cms.features.TeamCreator;
import com.oskarskalski.cms.json.JwtConfiguration;
import com.oskarskalski.cms.model.Article;
import com.oskarskalski.cms.repo.ArticleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteArticleService {
    private final ArticleRepo articleRepo;
    private final JwtConfiguration jwtConfiguration = new JwtConfiguration();

    @Autowired
    public DeleteArticleService(ArticleRepo articleRepo) {
        this.articleRepo = articleRepo;
    }

    public void softDeleteByArticleIdAndUserId(String articleId, String header) {
        Article article = articleRepo.findById(articleId)
                .orElseThrow(NullPointerException::new);

        DecodedJWT decodedJWT = jwtConfiguration.parse(header);
        long userId = Long.parseLong(decodedJWT.getClaim("id").asString());

        if(userId == article.getAuthorId()){
            article.setSoftDelete(!article.isSoftDelete());
            articleRepo.save(article);
        }
    }

    public void hardDeleteByArticleIdAndUserId(String articleId, String header) {
        Article article = articleRepo.findById(articleId)
                .orElseThrow(NullPointerException::new);

        DecodedJWT decodedJWT = jwtConfiguration.parse(header);
        long userId = Long.parseLong(decodedJWT.getClaim("id").asString());

        if(userId == article.getAuthorId()){
            articleRepo.deleteById(articleId);
        }
    }
}
