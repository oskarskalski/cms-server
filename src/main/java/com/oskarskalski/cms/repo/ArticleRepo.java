package com.oskarskalski.cms.repo;

import com.oskarskalski.cms.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepo extends JpaRepository<Article, String> {
    Optional<Article> findByAuthorId(long id);
    Optional<List<Article>> findAllByAuthorId(long id);
    Optional<Article> findByIdAndAuthorId(String id, long authorId);
    Optional<List<Article>> findAllByTeamId(String id);
}
