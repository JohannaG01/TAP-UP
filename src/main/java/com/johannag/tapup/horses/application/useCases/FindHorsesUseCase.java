package com.johannag.tapup.horses.application.useCases;

import com.johannag.tapup.auth.application.services.AuthenticationService;
import com.johannag.tapup.globals.infrastructure.utils.Logger;
import com.johannag.tapup.horses.application.dtos.FindHorsesDTO;
import com.johannag.tapup.horses.application.mappers.HorseApplicationMapper;
import com.johannag.tapup.horses.domain.dtos.FindHorsesEntityDTO;
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
    private final HorseApplicationMapper horseApplicationMapper;

    public Page<HorseModel> execute(FindHorsesDTO dto) {
        logger.info("Starting findHorses process");

        FindHorsesEntityDTO findHorsesEntityDTO = mapAndEnforceDefaultStateForNonAdmin(dto);
        Page<HorseModel> horses = horseRepository.findAll(findHorsesEntityDTO);

        logger.info("Finished findHorses process");
        return horses;
    }

    private FindHorsesEntityDTO mapAndEnforceDefaultStateForNonAdmin(FindHorsesDTO dto) {

        FindHorsesEntityDTO.Builder findHorsesEntityDTOBuilder =  horseApplicationMapper.toFindEntityDTO(dto);

        if (!authenticationService.isAdminUser()) {
            findHorsesEntityDTOBuilder.states(List.of(HorseModelState.ACTIVE));
        }

        return findHorsesEntityDTOBuilder.build();
    }
}
