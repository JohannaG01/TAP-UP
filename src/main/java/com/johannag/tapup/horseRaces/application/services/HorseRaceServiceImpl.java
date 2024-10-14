package com.johannag.tapup.horseRaces.application.services;

import com.johannag.tapup.horseRaces.application.dtos.CreateHorseRaceDTO;
import com.johannag.tapup.horseRaces.application.useCases.CreateHorseRaceUseCase;
import com.johannag.tapup.horseRaces.domain.models.HorseRaceModel;
import com.johannag.tapup.horses.application.exceptions.HorseNotAvailableException;
import com.johannag.tapup.horses.application.exceptions.HorseNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class HorseRaceServiceImpl implements HorseRaceService {

    private final CreateHorseRaceUseCase createHorseRaceUseCase;

    @Override
    public HorseRaceModel create(CreateHorseRaceDTO dto) throws HorseNotAvailableException, HorseNotFoundException {
        return createHorseRaceUseCase.execute(dto);
    }
}
