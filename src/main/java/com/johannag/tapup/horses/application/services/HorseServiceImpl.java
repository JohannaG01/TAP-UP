package com.johannag.tapup.horses.application.services;

import com.johannag.tapup.horses.application.dtos.CreateHorseDTO;
import com.johannag.tapup.horses.application.dtos.UpdateHorseDTO;
import com.johannag.tapup.horses.application.exceptions.CannotTransitionHorseStateException;
import com.johannag.tapup.horses.application.exceptions.HorseAlreadyExistsException;
import com.johannag.tapup.horses.application.exceptions.HorseNotFoundException;
import com.johannag.tapup.horses.application.exceptions.InvalidHorseStateException;
import com.johannag.tapup.horses.application.useCases.CreateHorseUseCase;
import com.johannag.tapup.horses.application.useCases.DeactivateHorseUseCase;
import com.johannag.tapup.horses.application.useCases.UpdateHorseUseCase;
import com.johannag.tapup.horses.domain.models.HorseModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class HorseServiceImpl implements HorseService {

    private final CreateHorseUseCase createHorseUseCase;
    private final UpdateHorseUseCase updateHorseUseCase;
    private final DeactivateHorseUseCase deactivateHorseUseCase;

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
}
