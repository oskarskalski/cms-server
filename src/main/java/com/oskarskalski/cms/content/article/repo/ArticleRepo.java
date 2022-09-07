package com.oskarskalski.cms.content.article.repo;

import com.oskarskalski.cms.content.article.model.Article;
import com.oskarskalski.cms.content.follow.model.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepo extends JpaRepository<Article, String> {
    Optional<Article> findByAuthorId(long id);
    Optional<List<Article>> findAllByAuthorIdOrderByDate(long id);
    Optional<Article> findByIdAndAuthorId(String id, long authorId);
    Optional<List<Article>> findAllByTeamId(String id);
}
