package com.oskarskalski.cms.content.comment.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.oskarskalski.cms.crud.operation.SecuredAdd;
import com.oskarskalski.cms.content.comment.model.dto.CommentDto;
import com.oskarskalski.cms.global.configuration.JwtConfiguration;
import com.oskarskalski.cms.content.comment.model.Comment;
import com.oskarskalski.cms.content.comment.repo.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AddCommentOpsService implements SecuredAdd<CommentDto> {
    private final CommentRepo commentRepo;
    private final JwtConfiguration jwtConfiguration = new JwtConfiguration();

    @Autowired
    public AddCommentOpsService(CommentRepo commentRepo) {
        this.commentRepo = commentRepo;
    }

    public void addByObjectAndAuthorizationHeader(CommentDto commentDto, String header){
        DecodedJWT decodedJWT = jwtConfiguration.parse(header);
        long userId = Long.parseLong(decodedJWT.getClaim("id").asString());

        Comment comment = new Comment();
        comment.setContent(commentDto.getContent());
        comment.setAuthorId(userId);
        comment.setArticleId(commentDto.getArticleId());

        comment.setSoftDelete(false);
        comment.setDate(new Date());

        commentRepo.save(comment);
    }
}
