package com.oskarskalski.cms.model;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Team {
    @Id
    private String id;

    @NonNull
    @Min(5)
    @Max(35)
    private String name;

    @NonNull
    @Min(10)
    @Max(100)
    private String description;

    private String code;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "teamId")
    private List<TeamMember> teamMembers;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<TeamMember> getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(List<TeamMember> teamMembers) {
        this.teamMembers = teamMembers;
    }
}
