package com.oskarskalski.cms.service.team;

import com.oskarskalski.cms.crud.operation.SecuredDelete;
import com.oskarskalski.cms.exception.AccessDeniedException;
import com.oskarskalski.cms.exception.NotFoundException;
import com.oskarskalski.cms.features.TeamCreator;
import com.oskarskalski.cms.model.Team;
import com.oskarskalski.cms.repo.TeamRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteTeamOpsService implements SecuredDelete<String> {
    private final TeamRepo teamRepo;
    private final TeamCreator teamCreator = new TeamCreator();

    @Autowired
    public DeleteTeamOpsService(TeamRepo teamRepo){
        this.teamRepo = teamRepo;
    }

    public void softDeleteByIdAndAuthorizationHeader(String id, String header) {
        Team team = teamRepo.findById(id)
                .orElseThrow(NotFoundException::new);

        if(teamCreator.getTeamCreator(team, header) != null){
            team.setSoftDelete(true);
        }else{
            throw new AccessDeniedException();
        }
    }

    public void hardDeleteByIdAndAuthorizationHeader(String id, String header) {
        Team team = teamRepo.findById(id)
                .orElseThrow(NotFoundException::new);

        if(teamCreator.getTeamCreator(team, header) != null){
            teamRepo.deleteById(id);
        }else{
            throw new AccessDeniedException();
        }
    }


}
