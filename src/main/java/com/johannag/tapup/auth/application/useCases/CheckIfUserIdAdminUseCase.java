package com.johannag.tapup.auth.application.useCases;

import com.johannag.tapup.auth.infrastructure.utils.SecurityContextUtils;
import com.johannag.tapup.globals.infrastructure.utils.Logger;
import com.johannag.tapup.horses.application.useCases.FindHorsesUseCase;
import com.johannag.tapup.users.infrastructure.framework.context.RoleOnContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class CheckIfUserIdAdminUseCase {

    private static final Logger logger = Logger.getLogger(CheckIfUserIdAdminUseCase.class);

    public boolean execute(){
        logger.info("Checking if user if admin");

        boolean isAdmin =  SecurityContextUtils
                .maybeRolesOnContext()
                .stream()
                .flatMap(Collection::stream)
                .anyMatch(role -> role == RoleOnContext.ADMIN);

        logger.info("User is: [{}]", isAdmin ? RoleOnContext.ADMIN : RoleOnContext.REGULAR);

        return isAdmin;
    }
}
