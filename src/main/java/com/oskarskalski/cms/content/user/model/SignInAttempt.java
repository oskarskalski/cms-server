package com.oskarskalski.cms.content.user.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class SignInAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String email;
    private boolean positive;
    private Date dateOfLastAttempt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isPositive() {
        return positive;
    }

    public void setPositive(boolean positive) {
        this.positive = positive;
    }

    public Date getDateOfLastAttempt() {
        return dateOfLastAttempt;
    }

    public void setDateOfLastAttempt(Date dateOfLastAttempt) {
        this.dateOfLastAttempt = dateOfLastAttempt;
    }
}
