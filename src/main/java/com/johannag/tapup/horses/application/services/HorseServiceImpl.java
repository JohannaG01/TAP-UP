package com.johannag.tapup.horses.application.services;

import com.johannag.tapup.horses.application.dtos.CreateHorseDTO;
import com.johannag.tapup.horses.application.dtos.FindHorsesDTO;
import com.johannag.tapup.horses.application.dtos.UpdateHorseDTO;
import com.johannag.tapup.horses.application.exceptions.CannotTransitionHorseStateException;
import com.johannag.tapup.horses.application.exceptions.HorseAlreadyExistsException;
import com.johannag.tapup.horses.application.exceptions.HorseNotFoundException;
import com.johannag.tapup.horses.application.exceptions.InvalidHorseStateException;
import com.johannag.tapup.horses.application.useCases.*;
import com.johannag.tapup.horses.domain.models.HorseModel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class HorseServiceImpl implements HorseService {

    private final CreateHorseUseCase createHorseUseCase;
    private final UpdateHorseUseCase updateHorseUseCase;
    private final DeactivateHorseUseCase deactivateHorseUseCase;
    private final FindHorsesUseCase findHorsesUseCase;
    private final FindHorseByUuidUseCase findHorseByUuidUseCase;

    @Override
    public HorseModel create(CreateHorseDTO dto) throws HorseAlreadyExistsException {
        return createHorseUseCase.execute(dto);
    }

    @Override
    public HorseModel update(UpdateHorseDTO dto) throws HorseNotFoundException,
            CannotTransitionHorseStateException, InvalidHorseStateException {
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
    public HorseModel findByUuid(UUID uuid) throws HorseNotFoundException {
        return findHorseByUuidUseCase.execute(uuid);
    }
}
