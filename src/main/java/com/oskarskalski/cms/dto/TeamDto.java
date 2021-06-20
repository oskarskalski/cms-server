package com.oskarskalski.cms.dto;

import com.oskarskalski.cms.model.TeamMember;

import java.util.List;

public class TeamDto {
    private String name;
    private String description;
    private List<TeamMember> teamMembers;

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

    public List<TeamMember> getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(List<TeamMember> teamMembers) {
        this.teamMembers = teamMembers;
    }
}
