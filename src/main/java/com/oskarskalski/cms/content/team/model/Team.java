package com.oskarskalski.cms.content.team.model;

import com.oskarskalski.cms.content.member.model.TeamMember;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    private boolean softDelete;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "teamId")
    private List<TeamMember> teamMembers;
}
