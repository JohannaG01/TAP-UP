package com.johannag.tapup.horseRaces.application.useCases;

import com.johannag.tapup.globals.infrastructure.utils.Logger;
import com.johannag.tapup.horseRaces.application.dtos.FindHorseRacesDTO;
import com.johannag.tapup.horseRaces.application.mappers.HorseRaceApplicationMapper;
import com.johannag.tapup.horseRaces.domain.dtos.FindHorseRaceEntitiesDTO;
import com.johannag.tapup.horseRaces.domain.models.HorseRaceModel;
import com.johannag.tapup.horseRaces.infrastructure.db.adapters.HorseRaceRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FindHorseRacesUseCase {

    private static final Logger logger = Logger.getLogger(FindHorseRacesUseCase.class);
    private final HorseRaceRepository horseRaceRepository;
    private final HorseRaceApplicationMapper horseRaceApplicationMapper;

    public Page<HorseRaceModel> execute(FindHorseRacesDTO dto) {
        logger.info("Starting FindHorseRaces process");

        FindHorseRaceEntitiesDTO findHorseRaceEntitiesDTO = horseRaceApplicationMapper.toFindEntitiesDTO(dto);
        Page<HorseRaceModel> horseRaces = horseRaceRepository.findAll(findHorseRaceEntitiesDTO);

        logger.info("Finished FindHorseRaces process");
        return horseRaces;

    }

}
