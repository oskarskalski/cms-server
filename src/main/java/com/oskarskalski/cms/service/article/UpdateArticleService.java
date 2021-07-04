package com.oskarskalski.cms.service.article;

import com.oskarskalski.cms.dto.ArticleDto;
import com.oskarskalski.cms.exception.AccessDeniedException;
import com.oskarskalski.cms.exception.InvalidDataException;
import com.oskarskalski.cms.exception.NotFoundException;
import com.oskarskalski.cms.model.Article;
import com.oskarskalski.cms.repo.ArticleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateArticleService {
    private final ArticleRepo articleRepo;

    @Autowired
    public UpdateArticleService(ArticleRepo articleRepo) {
        this.articleRepo = articleRepo;
    }


    public void updateArticle(ArticleDto articleDto, String header, String id) {
        if (header == null || !header.startsWith("Bearer")) {
            throw new AccessDeniedException();
        }

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

        Article article = articleRepo.findById(id)
                .orElseThrow(NotFoundException::new);

        if (articleDto.getTitle() != null && !article.getTitle().equals(articleDto.getTitle())) {
            article.setTitle(articleDto.getTitle());
        }
        if (articleDto.getContent() != null && !article.getContent().equals(articleDto.getContent())) {
            article.setContent(articleDto.getContent());
        }

        articleRepo.save(article);
    }
}
