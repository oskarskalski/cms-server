package com.oskarskalski.cms.service;

import com.oskarskalski.cms.model.Comment;
import com.oskarskalski.cms.repo.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.oskarskalski.cms.dto.CommentDto;

import java.util.Date;

@Service
public class CommentService {
    private final CommentRepo commentRepo;

    @Autowired
    public CommentService(CommentRepo commentRepo) {
        this.commentRepo = commentRepo;
    }

    public CommentDto getCommentById(long id){
        CommentDto commentDto = commentRepo.findById(id)
                .orElseThrow(NullPointerException::new)
                .convertToDto();

        return commentDto;
    }

    public void addComment(Comment comment, long authorId){
        comment.setAuthorId(authorId);
        comment.setDate(new Date());

        commentRepo.save(comment);
    }

    public void softDeleteComment(long commentId, long authorId){
        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(NullPointerException::new);

        if(comment.getId() == authorId){
            comment.setSoftDelete(true);

            commentRepo.save(comment);
        }
    }
}
