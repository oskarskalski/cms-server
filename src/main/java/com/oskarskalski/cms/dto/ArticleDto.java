package com.oskarskalski.cms.dto;

import com.oskarskalski.cms.model.Article;
import com.oskarskalski.cms.model.Comment;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

public class ArticleDto {
    private String id;
    private String title;
    private String content;
    private String authorName;
    private Date date;
    private String[] images;
    private List<Comment> commentDto;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public List<Comment> getCommentDto() {
        return commentDto;
    }

    public void setCommentDto(List<Comment> commentDto) {
        this.commentDto = commentDto;
    }
}
