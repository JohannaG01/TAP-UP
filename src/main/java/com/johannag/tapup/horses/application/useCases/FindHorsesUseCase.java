package com.johannag.tapup.horses.application.useCases;

import com.johannag.tapup.auth.application.services.AuthenticationService;
import com.johannag.tapup.globals.infrastructure.utils.Logger;
import com.johannag.tapup.horses.application.dtos.FindHorsesDTO;
import com.johannag.tapup.horses.domain.models.HorseModel;
import com.johannag.tapup.horses.domain.models.HorseModelState;
import com.johannag.tapup.horses.infrastructure.db.adapters.HorseRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FindHorsesUseCase {

    private static final Logger logger = Logger.getLogger(FindHorsesUseCase.class);
    private final HorseRepository horseRepository;
    private final AuthenticationService authenticationService;

    public Page<HorseModel> execute(FindHorsesDTO dto){
        logger.info("Starting findHorses process");

        boolean isAdminUser = isAdminUser();
        applyDefaultStateToDTOIfUserIsNotAdmin(dto, isAdminUser);
        Page<HorseModel> horses = horseRepository.findAll(dto);
        removeStateFromModelIfUserIsNotAdmin(horses, isAdminUser);

        logger.info("Finished findHorses process");
        return horses;
    }

    private boolean isAdminUser(){
        return authenticationService.isAdminUser();
    }

    private void applyDefaultStateToDTOIfUserIsNotAdmin(FindHorsesDTO dto, boolean isAdminUser){
        if (!isAdminUser){
            dto.setStates(List.of(HorseModelState.ACTIVE));
        }
    }

    private void removeStateFromModelIfUserIsNotAdmin(Page<HorseModel> horses, boolean isAdminUser){
        if (!isAdminUser){
            horses.forEach(horse -> horse.setState(null));
        }
    }
}
