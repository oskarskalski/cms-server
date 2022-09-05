package com.oskarskalski.cms.content.article.model;

import com.oskarskalski.cms.content.comment.model.Comment;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Article {
    @Id
    private String id;
    private String title;
    private String content;
    private String namesOfImages;
    private long authorId;
    private String teamId;
    private Date date;
    private boolean softDelete;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "articleId")
    private List<Comment> comments;

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

    public String getNamesOfImages() {
        return namesOfImages;
    }

    public void setNamesOfImages(String namesOfImages) {
        this.namesOfImages = namesOfImages;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isSoftDelete() {
        return softDelete;
    }

    public void setSoftDelete(boolean softDelete) {
        this.softDelete = softDelete;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
