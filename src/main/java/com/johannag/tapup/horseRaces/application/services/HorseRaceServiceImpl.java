package com.johannag.tapup.horseRaces.application.services;

import com.johannag.tapup.horseRaces.application.dtos.CreateHorseRaceDTO;
import com.johannag.tapup.horseRaces.application.dtos.FindHorseRacesDTO;
import com.johannag.tapup.horseRaces.application.dtos.UpdateHorseRaceDTO;
import com.johannag.tapup.horseRaces.application.useCases.CreateHorseRaceUseCase;
import com.johannag.tapup.horseRaces.application.useCases.FindHorseRacesUseCase;
import com.johannag.tapup.horseRaces.application.useCases.FindOneHorseRaceUseCase;
import com.johannag.tapup.horseRaces.application.useCases.UpdateHorseRaceUseCase;
import com.johannag.tapup.horseRaces.domain.models.HorseRaceModel;
import com.johannag.tapup.horseRaces.exceptions.HorseRaceNotFoundException;
import com.johannag.tapup.horseRaces.exceptions.InvalidHorseRaceStateException;
import com.johannag.tapup.horses.application.exceptions.HorseNotAvailableException;
import com.johannag.tapup.horses.application.exceptions.HorseNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class HorseRaceServiceImpl implements HorseRaceService {

    private final CreateHorseRaceUseCase createHorseRaceUseCase;
    private final UpdateHorseRaceUseCase updateHorseRaceUseCase;
    private final FindOneHorseRaceUseCase findOneHorseRaceUseCase;
    private final FindHorseRacesUseCase findHorseRacesUseCase;

    @Override
    public HorseRaceModel create(CreateHorseRaceDTO dto) throws HorseNotAvailableException, HorseNotFoundException {
        return createHorseRaceUseCase.execute(dto);
    }

    @Override
    public HorseRaceModel update(UpdateHorseRaceDTO dto) throws HorseRaceNotFoundException,
            InvalidHorseRaceStateException, HorseNotAvailableException {
        return updateHorseRaceUseCase.execute(dto);
    }

    @Override
    public HorseRaceModel findOneByUuid(UUID uuid) throws HorseRaceNotFoundException {
        return findOneHorseRaceUseCase.execute(uuid);
    }

    @Override
    public Page<HorseRaceModel> findAll(FindHorseRacesDTO dto) {
        return findHorseRacesUseCase.execute(dto);
    }
}
