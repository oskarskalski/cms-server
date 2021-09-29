package com.oskarskalski.cms.crud.operation;

import com.oskarskalski.cms.dto.ArticleDto;

import java.util.List;

public interface GetArticle extends Get<ArticleDto, String> {
    List<ArticleDto> getAllArticlesByAuthorId(long id);
    List<ArticleDto> getAllArticlesByTeamId(String id);
}
