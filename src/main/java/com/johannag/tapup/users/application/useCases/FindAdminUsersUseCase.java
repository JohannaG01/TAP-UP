package com.johannag.tapup.users.application.useCases;

import com.johannag.tapup.globals.infrastructure.utils.Logger;
import com.johannag.tapup.users.domain.models.UserModel;
import com.johannag.tapup.users.infrastructure.db.adapter.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FindAdminUsersUseCase {

    private static final Logger logger = Logger.getLogger(FindAdminUsersUseCase.class);
    private final UserRepository userRepository;

    public List<UserModel> execute() {
        logger.info("Starting process for finding admin users");

        List<UserModel> admin = userRepository.findAllAdmins();

        logger.info("Finished process for finding admin users");
        return admin;
    }
}
