package com.oskarskalski.cms.content.comment.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.oskarskalski.cms.global.configuration.JwtConfiguration;
import com.oskarskalski.cms.content.comment.model.Comment;
import com.oskarskalski.cms.content.comment.repo.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteCommentOpsService{
    private final CommentRepo commentRepo;
    private final JwtConfiguration jwtConfiguration = new JwtConfiguration();

    @Autowired
    public DeleteCommentOpsService(CommentRepo commentRepo) {
        this.commentRepo = commentRepo;
    }

    public void softDeleteByIdAndAuthorizationHeader(Long commentId, String header) {
        DecodedJWT decodedJWT = jwtConfiguration.parse(header);
        long userId = Long.parseLong(decodedJWT.getClaim("id").asString());

        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(NullPointerException::new);

        if (comment.getId() == userId) {
            comment.setSoftDelete(true);

            commentRepo.save(comment);
        }
    }

    public void hardDeleteByIdAndAuthorizationHeader(Long commentId, String header) {
        DecodedJWT decodedJWT = jwtConfiguration.parse(header);
        long userId = Long.parseLong(decodedJWT.getClaim("id").asString());

        commentRepo.deleteById(commentId);
    }
}
