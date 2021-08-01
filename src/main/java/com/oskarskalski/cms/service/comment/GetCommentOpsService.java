package com.oskarskalski.cms.service.comment;

import com.oskarskalski.cms.crud.operation.Get;
import com.oskarskalski.cms.dto.CommentDto;
import com.oskarskalski.cms.exception.NotFoundException;
import com.oskarskalski.cms.model.Comment;
import com.oskarskalski.cms.repo.CommentRepo;
import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetCommentOpsService implements Get<CommentDto, Long> {
    private final CommentRepo commentRepo;

    @Autowired
    public GetCommentOpsService(CommentRepo commentRepo) {
        this.commentRepo = commentRepo;
    }

    public CommentDto getById(Long id){
        Comment comment = commentRepo.findById(id)
                .orElseThrow(NotFoundException::new);

        if(comment.isSoftDelete())
            throw new NotFoundException();

        CommentDto commentDto = new CommentDto();
        commentDto.setDate(comment.getDate());
        commentDto.setId(comment.getId());
        commentDto.setContent(comment.getContent());
        commentDto.setUserId(comment.getAuthorId());

        return commentDto;
    }
}
