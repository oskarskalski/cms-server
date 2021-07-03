package com.oskarskalski.cms.service.team;

import com.oskarskalski.cms.dto.TeamDto;
import com.oskarskalski.cms.exception.AccessDeniedException;
import com.oskarskalski.cms.exception.InvalidDataException;
import com.oskarskalski.cms.exception.NotFoundException;
import com.oskarskalski.cms.features.CodeGenerator;
import com.oskarskalski.cms.features.TeamCreator;
import com.oskarskalski.cms.json.JwtConfiguration;
import com.oskarskalski.cms.model.Team;
import com.oskarskalski.cms.model.TeamMember;
import com.oskarskalski.cms.repo.TeamRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateTeamService{
    private final TeamRepo teamRepo;
    private final TeamCreator teamCreator = new TeamCreator();

    @Autowired
    public UpdateTeamService(TeamRepo teamRepo) {
        this.teamRepo = teamRepo;
    }

    public void updateByDtoAndHeader(TeamDto teamDto, String header) {
        if (teamDto.getDescription() == null && teamDto.getName() == null)
            throw new InvalidDataException();

        Team team = teamRepo.findById(teamDto.getId())
                .orElseThrow(NotFoundException::new);
        TeamMember teamMember = teamCreator.getTeamCreator(team, header);

        if (teamMember != null) {
            boolean updated = false;
            if (teamDto.getName() != null &&
                    teamDto.getName().length() >= 5 && teamDto.getName().length() <= 35) {
                team.setName(teamDto.getName());
                updated = true;
            }

            if (teamDto.getDescription() != null
                    && teamDto.getDescription().length() >= 10 && teamDto.getDescription().length() <= 100) {
                team.setDescription(teamDto.getDescription());
                updated = true;
            }

            if (updated)
                teamRepo.save(team);
            else
                throw new InvalidDataException();
        } else
            throw new AccessDeniedException();
    }


    public void updateTeamCode(String id, String header) {
        Team team = teamRepo.findById(id)
                .orElseThrow(NotFoundException::new);

        TeamMember teamMember = teamCreator.getTeamCreator(team, header);

        if (teamMember != null) {
            int codeLength = 6;
            team.setCode(CodeGenerator.generate(codeLength));
            teamRepo.save(team);
        } else
            throw new AccessDeniedException();
    }
}
