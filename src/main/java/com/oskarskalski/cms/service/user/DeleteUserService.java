package com.oskarskalski.cms.service.user;

import com.oskarskalski.cms.model.User;
import com.oskarskalski.cms.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteUserService {
    private final UserRepo userRepo;

    @Autowired
    public DeleteUserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public void hardDeleteByIdAndHeader(Long id, String header){
        userRepo.deleteById(id);
    }

    public void softDeleteByIdAndHeader(Long id, String header) {
        User user = userRepo.findById(id)
                .orElseThrow(NullPointerException::new);

        user.setSoftDelete(true);

        userRepo.save(user);
    }
}
