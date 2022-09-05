package com.oskarskalski.cms.content.user.service;

import com.oskarskalski.cms.crud.operation.SecuredDelete;
import com.oskarskalski.cms.content.user.model.User;
import com.oskarskalski.cms.content.user.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteUserOpsService implements SecuredDelete<Long> {
    private final UserRepo userRepo;

    @Autowired
    public DeleteUserOpsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public void softDeleteByIdAndAuthorizationHeader(Long id, String header) {
        User user = userRepo.findById(id)
                .orElseThrow(NullPointerException::new);

        user.setSoftDelete(true);

        userRepo.save(user);
    }

    public void hardDeleteByIdAndAuthorizationHeader(Long id, String header){
        userRepo.deleteById(id);
    }

}
