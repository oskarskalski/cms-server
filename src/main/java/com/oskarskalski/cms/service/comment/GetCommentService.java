package com.oskarskalski.cms.service.comment;

import com.oskarskalski.cms.dto.CommentDto;
import com.oskarskalski.cms.model.Comment;
import com.oskarskalski.cms.repo.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetCommentService {
    private final CommentRepo commentRepo;

    @Autowired
    public GetCommentService(CommentRepo commentRepo) {
        this.commentRepo = commentRepo;
    }

    public CommentDto getCommentById(long id){
        Comment comment = commentRepo.findById(id)
                .orElseThrow(NullPointerException::new);

        CommentDto commentDto = new CommentDto();
        commentDto.setDate(comment.getDate());
        commentDto.setId(comment.getId());
        commentDto.setContent(comment.getContent());
        commentDto.setUserId(comment.getAuthorId());

        return commentDto;
    }
}
