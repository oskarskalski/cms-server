package com.oskarskalski.cms.service.article;

import com.oskarskalski.cms.crud.operation.Get;
import com.oskarskalski.cms.crud.operation.GetArticle;
import com.oskarskalski.cms.dto.ArticleDto;
import com.oskarskalski.cms.exception.NotFoundException;
import com.oskarskalski.cms.model.Article;
import com.oskarskalski.cms.model.Comment;
import com.oskarskalski.cms.repo.ArticleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetArticleOpsService implements GetArticle {
    private final ArticleRepo articleRepo;

    @Autowired
    public GetArticleOpsService(ArticleRepo articleRepo) {
        this.articleRepo = articleRepo;
    }

    public ArticleDto getById(String id) {
        Article article = articleRepo.findById(id)
                .orElseThrow(NullPointerException::new);

        if (article.isSoftDelete())
            throw new NotFoundException();

        ArticleDto articleDto = mapToArticleDto(article);

        return articleDto;
    }


    public List<ArticleDto> getAllArticlesByAuthorId(long authorId) {
        List<Article> articles = articleRepo.findAllByAuthorId(authorId)
                .orElseThrow(NullPointerException::new);

        List<ArticleDto> articlesDto = getArticlesDto(articles);

        return articlesDto;
    }

    public List<ArticleDto> getArticlesByTeamId(String teamId) {
        List<Article> articles = articleRepo.findAllByTeamId(teamId)
                .orElseThrow(NullPointerException::new);

        List<ArticleDto> articlesDto = getArticlesDto(articles);

        return articlesDto;
    }

    private List<ArticleDto> getArticlesDto(List<Article> articles) {
        List<ArticleDto> articlesDto = new ArrayList<>();

        for (Article article : articles) {
            if (!article.isSoftDelete()) {
                articlesDto.add(mapToArticleDto(article));
            }
        }

        if (articlesDto.size() == 0)
            throw new NotFoundException();
        else
            return articlesDto;
    }

    private ArticleDto mapToArticleDto(Article article) {
        if(article.isSoftDelete())
            throw new NotFoundException();
        
        if (article.getComments().size() != 0 && article.getComments() != null) {
            for (int i = 0; i < article.getComments().size(); i++) {
                Comment comment = article.getComments().get(i);
                if (comment.isSoftDelete()) {
                    article.getComments().remove(i);
                }
            }
        }
        ArticleDto articleDto = new ArticleDto();

        final String uri = "http://localhost:8080/api/users/fullName/" + article.getAuthorId();

        RestTemplate restTemplate = new RestTemplate();
        String fullNameAuthor = restTemplate.getForObject(uri, String.class);

        articleDto.setTitle(article.getTitle());
        articleDto.setAuthorName(fullNameAuthor);
        articleDto.setContent(article.getContent());
        articleDto.setDate(article.getDate());
        articleDto.setCommentDto(article.getComments());

        if (article.getNamesOfImages() != null) {
            articleDto.setImages(article.getNamesOfImages().split(" "));

        }

        return articleDto;
    }
}

