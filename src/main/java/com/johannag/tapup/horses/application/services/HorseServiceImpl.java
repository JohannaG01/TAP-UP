package com.johannag.tapup.horses.application.services;

import com.johannag.tapup.horses.application.dtos.CreateHorseDTO;
import com.johannag.tapup.horses.application.exceptions.HorseAlreadyExistsException;
import com.johannag.tapup.horses.application.useCases.CreateHorseUseCase;
import com.johannag.tapup.horses.domain.models.HorseModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class HorseServiceImpl implements HorseService {

    private final CreateHorseUseCase createHorseUseCase;

    @Override
    public HorseModel create(CreateHorseDTO dto) throws HorseAlreadyExistsException {
        return createHorseUseCase.execute(dto);
    }
}
