package com.johannag.tapup.horses.application.services;

import com.johannag.tapup.horses.application.dtos.CreateHorseDTO;
import com.johannag.tapup.horses.application.dtos.FindHorsesDTO;
import com.johannag.tapup.horses.application.dtos.UpdateHorseDTO;
import com.johannag.tapup.horses.application.exceptions.CannotTransitionHorseStateException;
import com.johannag.tapup.horses.application.exceptions.HorseAlreadyExistsException;
import com.johannag.tapup.horses.application.exceptions.HorseNotAvailableException;
import com.johannag.tapup.horses.application.exceptions.HorseNotFoundException;
import com.johannag.tapup.horses.application.useCases.*;
import com.johannag.tapup.horses.domain.models.HorseModel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class HorseServiceImpl implements HorseService {

    private final CreateHorseUseCase createHorseUseCase;
    private final UpdateHorseUseCase updateHorseUseCase;
    private final DeactivateHorseUseCase deactivateHorseUseCase;
    private final FindHorsesUseCase findHorsesUseCase;
    private final FindOneHorseByUuidUseCase findOneHorseByUuidUseCase;
    private final ValidateHorsesAvailabilityUseCase validateHorsesAvailabilityUseCase;

    @Override
    public HorseModel create(CreateHorseDTO dto) throws HorseAlreadyExistsException {
        return createHorseUseCase.execute(dto);
    }

    @Override
    public HorseModel update(UpdateHorseDTO dto) throws HorseNotFoundException,
            CannotTransitionHorseStateException {
        return updateHorseUseCase.execute(dto);
    }

    @Override
    public HorseModel delete(UUID uuid) throws HorseNotFoundException, CannotTransitionHorseStateException {
        return deactivateHorseUseCase.execute(uuid);
    }

    @Override
    public Page<HorseModel> findAll(FindHorsesDTO dto) {
        return findHorsesUseCase.execute(dto);
    }

    @Override
    public HorseModel findOneByUuid(UUID uuid) throws HorseNotFoundException {
        return findOneHorseByUuidUseCase.execute(uuid);
    }

    @Override
    public void validateHorsesAvailability(List<UUID> uuids, LocalDateTime raceStartTime) throws HorseNotFoundException,
            HorseNotAvailableException {
        validateHorsesAvailabilityUseCase.execute(uuids, raceStartTime);
    }

}
