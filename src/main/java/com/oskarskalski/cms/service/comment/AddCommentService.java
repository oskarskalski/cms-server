package com.oskarskalski.cms.service.comment;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.oskarskalski.cms.dto.CommentDto;
import com.oskarskalski.cms.json.JwtConfiguration;
import com.oskarskalski.cms.model.Comment;
import com.oskarskalski.cms.repo.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AddCommentService {
    private final CommentRepo commentRepo;
    private final JwtConfiguration jwtConfiguration = new JwtConfiguration();

    @Autowired
    public AddCommentService(CommentRepo commentRepo) {
        this.commentRepo = commentRepo;
    }

    public void addComment(CommentDto commentDto, String header, String articleId){
        DecodedJWT decodedJWT = jwtConfiguration.parse(header);
        long userId = Long.parseLong(decodedJWT.getClaim("id").asString());

        Comment comment = new Comment();
        comment.setContent(commentDto.getContent());
        comment.setAuthorId(userId);
        comment.setArticleId(articleId);

        comment.setSoftDelete(false);
        comment.setDate(new Date());

        commentRepo.save(comment);
    }
}
