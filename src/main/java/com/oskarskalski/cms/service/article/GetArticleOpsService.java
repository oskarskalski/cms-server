package com.oskarskalski.cms.service.article;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oskarskalski.cms.crud.operation.GetArticle;
import com.oskarskalski.cms.dto.ArticleDto;
import com.oskarskalski.cms.exception.NotFoundException;
import com.oskarskalski.cms.model.Article;
import com.oskarskalski.cms.model.Comment;
import com.oskarskalski.cms.repo.ArticleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

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
        List<Article> articles = articleRepo.findAllByAuthorIdOrderByDate(authorId)
                .orElseThrow(NullPointerException::new);

        List<ArticleDto> articlesDto = getArticlesDto(articles, 50);

        return articlesDto;
    }

    public List<ArticleDto> getAllArticlesByTeamId(String teamId) {
        List<Article> articles = articleRepo.findAllByTeamId(teamId)
                .orElseThrow(NullPointerException::new);

        List<ArticleDto> articlesDto = getArticlesDto(articles, 50);

        return articlesDto;
    }

    public List<ArticleDto> getAllArticlesBy(String header) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", header);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/api/follow/secure", HttpMethod.GET, entity, String.class);

        List<Article> articles = new ArrayList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Map> list = objectMapper.readValue(response.getBody(), List.class);

            for(Map map: list){
                List<Article> getArticles = articleRepo.findAllByAuthorIdOrderByDate(Long.parseLong(map.get("followingId").toString())).get();
                articles.addAll(getArticles);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return getArticlesDto(articles, 50);
    }

    private List<ArticleDto> getArticlesDto(List<Article> articles, int contentLength) {
        List<ArticleDto> articlesDto = new ArrayList<>();

        for (Article article : articles) {
            if (!article.isSoftDelete()) {
                ArticleDto articleDto = mapToArticleDto(article);
                articleDto.setContent(articleDto.getContent().substring(0, contentLength));
                articlesDto.add(articleDto);
            }
        }

        if (articlesDto.size() == 0)
            throw new NotFoundException();
        else
            return articlesDto;
    }

    private ArticleDto mapToArticleDto(Article article) {
        if (article.isSoftDelete())
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

        articleDto.setId(article.getId());
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

