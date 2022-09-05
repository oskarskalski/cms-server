package com.oskarskalski.cms.content.comment.repo;


import com.oskarskalski.cms.content.comment.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {

}
