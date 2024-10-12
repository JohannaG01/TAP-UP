package com.johannag.tapup.horses.application.useCases;

import com.johannag.tapup.globals.infrastructure.utils.Logger;
import com.johannag.tapup.horses.application.dtos.CreateHorseDTO;
import com.johannag.tapup.horses.application.exceptions.HorseAlreadyExistsException;
import com.johannag.tapup.horses.application.mappers.HorseApplicationMapper;
import com.johannag.tapup.horses.domain.dtos.CreateHorseEntityDTO;
import com.johannag.tapup.horses.domain.models.HorseModel;
import com.johannag.tapup.horses.infrastructure.db.adapters.HorseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateHorseUseCase {

    private static final Logger logger = Logger.getLogger(CreateHorseUseCase.class);
    private final HorseRepository horseRepository;
    private final HorseApplicationMapper horseApplicationMapper;

    public HorseModel execute(CreateHorseDTO dto) throws HorseAlreadyExistsException {
        logger.info("Starting CreateHorse process for horse [{}]", dto.getCode());

        validateHorseDoesNotExistsOrThrow(dto.getCode());
        CreateHorseEntityDTO createHorseEntityDTO = horseApplicationMapper.toCreateEntityDTO(dto);
        HorseModel horseModel = horseRepository.upsert(createHorseEntityDTO);

        logger.info("CreateHorse process for horse {} has finished", dto.getCode());
        return horseModel;
    }

    private void validateHorseDoesNotExistsOrThrow(String code) {
        logger.info("Validating weather or not horse with code [{}] exists", code);
        if (horseRepository.existsHorseByCode(code)) {
            throw new HorseAlreadyExistsException(code);
        }
    }
}
