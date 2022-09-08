package com.oskarskalski.cms.content.article.model;

import com.oskarskalski.cms.content.comment.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
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
}
