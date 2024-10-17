package com.johannag.tapup.horseRaces.application.useCases;

import com.johannag.tapup.globals.infrastructure.utils.Logger;
import com.johannag.tapup.horseRaces.application.dtos.FindHorseRacesDTO;
import com.johannag.tapup.horseRaces.application.mappers.HorseRaceApplicationMapper;
import com.johannag.tapup.horseRaces.domain.dtos.FindHorseRacesEntityDTO;
import com.johannag.tapup.horseRaces.domain.models.HorseRaceModel;
import com.johannag.tapup.horseRaces.infrastructure.db.adapters.HorseRaceRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FindHorseRacesUseCase {

    private final HorseRaceRepository horseRaceRepository;
    private final HorseRaceApplicationMapper horseRaceApplicationMapper;
    private static final Logger logger = Logger.getLogger(FindHorseRacesUseCase.class);

    public Page<HorseRaceModel> execute(FindHorseRacesDTO dto) {
        logger.info("Starting FindHorseRaces process");

        FindHorseRacesEntityDTO findHorseRacesEntityDTO = horseRaceApplicationMapper.toFindEntityDTO(dto);
        Page<HorseRaceModel> horseRaces = horseRaceRepository.findAll(findHorseRacesEntityDTO);

        logger.info("Finished FindHorseRaces process");
        return horseRaces;

    }

}
