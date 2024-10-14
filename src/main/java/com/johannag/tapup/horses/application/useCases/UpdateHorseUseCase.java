package com.johannag.tapup.horses.application.useCases;

import com.johannag.tapup.globals.infrastructure.utils.Logger;
import com.johannag.tapup.horses.application.dtos.UpdateHorseDTO;
import com.johannag.tapup.horses.application.exceptions.CannotTransitionHorseStateException;
import com.johannag.tapup.horses.application.exceptions.HorseNotFoundException;
import com.johannag.tapup.horses.application.mappers.HorseApplicationMapper;
import com.johannag.tapup.horses.domain.dtos.UpdateHorseEntityDTO;
import com.johannag.tapup.horses.domain.models.HorseModel;
import com.johannag.tapup.horses.infrastructure.db.adapters.HorseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.johannag.tapup.horses.application.exceptions.HorseExceptionCode.HORSE_IN_SCHEDULED_MATCH;

@Service
@AllArgsConstructor
public class UpdateHorseUseCase {

    private static final Logger logger = Logger.getLogger(UpdateHorseUseCase.class);
    private final HorseRepository horseRepository;
    private final HorseApplicationMapper horseApplicationMapper;
    private final FindOneHorseByUuidUseCase findOneHorseByUuidUseCase;

    public HorseModel execute(UpdateHorseDTO dto) throws HorseNotFoundException,
            CannotTransitionHorseStateException {
        logger.info("Starting UpdateHorse process for horse with UUID [{}]", dto.getUuid());

        findOneHorseByUuidUseCase.execute(dto.getUuid());
        validateCanUpdateHorseOrThrow(dto);
        UpdateHorseEntityDTO updateHorseEntityDTO = horseApplicationMapper.toUpdateEntityDTO(dto);
        HorseModel horseModel = horseRepository.update(updateHorseEntityDTO);

        logger.info("Finished UpdateHorse process for horse with UUID [{}]", dto.getUuid());
        return horseModel;
    }

    public void validateCanUpdateHorseOrThrow(UpdateHorseDTO dto) {
        if (dto.isStateTemporallyInactive() && horseRepository.isHorseInScheduledMatch(dto.getUuid())) {
            throw new CannotTransitionHorseStateException(String.format(
                    "Unable to update state to [TEMPORALLY_INACTIVE]. The horse with UUID %s is currently in a " +
                            "scheduled race. Please cancel the corresponding race and try again.", dto.getUuid()),
                    HORSE_IN_SCHEDULED_MATCH.toString());
        }
    }
}
