package com.oskarskalski.cms.model;

import com.oskarskalski.cms.dto.ArticleDto;
import com.sun.istack.NotNull;
import org.springframework.web.client.RestTemplate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

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

    public ArticleDto convertToDto(){
        ArticleDto articleDto = new ArticleDto();

        final String uri = "http://localhost:8080/api/users/fullName/" + getAuthorId();

        RestTemplate restTemplate = new RestTemplate();
        String fullNameAuthor = restTemplate.getForObject(uri, String.class);

        articleDto.setTitle(this.getTitle());
        articleDto.setAuthorName(fullNameAuthor);
        articleDto.setContent(this.getContent());
        articleDto.setDate(this.getDate());

        if(this.getNamesOfImages() != null){
            articleDto.setImages(this.getNamesOfImages().split(" "));

        }

        return articleDto;
    }

}
