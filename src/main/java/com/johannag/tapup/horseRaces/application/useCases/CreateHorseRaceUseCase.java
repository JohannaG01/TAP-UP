package com.johannag.tapup.horseRaces.application.useCases;

import com.johannag.tapup.globals.infrastructure.utils.Logger;
import com.johannag.tapup.horseRaces.application.dtos.CreateHorseRaceDTO;
import com.johannag.tapup.horseRaces.domain.dtos.CreateHorseRaceEntityDTO;
import com.johannag.tapup.horses.application.exceptions.HorseNotAvailableException;
import com.johannag.tapup.horseRaces.application.mappers.HorseRaceApplicationMapper;
import com.johannag.tapup.horseRaces.domain.models.HorseRaceModel;
import com.johannag.tapup.horseRaces.infrastructure.db.adapters.HorseRaceRepository;
import com.johannag.tapup.horses.application.exceptions.HorseNotFoundException;
import com.johannag.tapup.horses.application.services.HorseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateHorseRaceUseCase {

    private static final Logger logger = Logger.getLogger(CreateHorseRaceUseCase.class);
    private final HorseRaceApplicationMapper horseRaceApplicationMapper;
    private final HorseService horseService;
    private final HorseRaceRepository horseRaceRepository;

    public HorseRaceModel execute(CreateHorseRaceDTO dto) throws HorseNotFoundException, HorseNotAvailableException {
        logger.info("Starting CreateHorseRace process");

        horseService.validateHorsesAvailability(dto.getHorsesUuids(), dto.getStartTime());
        CreateHorseRaceEntityDTO createHorseRaceEntityDTO = horseRaceApplicationMapper.toCreateEntityDTO(dto);
        HorseRaceModel horseRaceModel = horseRaceRepository.create(createHorseRaceEntityDTO);

        logger.info("CreateHorseRace process for horse has finished");
        return horseRaceModel;
    }


}
