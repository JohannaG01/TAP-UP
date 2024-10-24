package com.johannag.tapup.horses.application.useCases;

import com.johannag.tapup.horses.application.dtos.UpdateHorseDTO;
import com.johannag.tapup.horses.application.exceptions.CannotTransitionHorseStateException;
import com.johannag.tapup.horses.application.exceptions.HorseNotFoundException;
import com.johannag.tapup.horses.application.mappers.HorseApplicationMapper;
import com.johannag.tapup.horses.application.useCases.stubs.HorseStubs;
import com.johannag.tapup.horses.domain.dtos.UpdateHorseEntityDTO;
import com.johannag.tapup.horses.domain.models.HorseModel;
import com.johannag.tapup.horses.infrastructure.db.adapters.HorseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.UUID;

import static com.johannag.tapup.horses.domain.models.HorseModelState.ACTIVE;
import static com.johannag.tapup.horses.domain.models.HorseModelState.TEMPORALLY_INACTIVE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UpdateHorseUseCaseTests {

    private final UpdateHorseDTO updateHorseDTO = HorseStubs.updateHorseDTO(ACTIVE);
    private final UpdateHorseDTO temporallyInactiveUpdateHorseDTO = HorseStubs.updateHorseDTO(TEMPORALLY_INACTIVE);
    private final UpdateHorseEntityDTO updateHorseEntityDTO = HorseStubs.updateHorseEntityDTO();
    private final HorseModel horse = HorseStubs.horseModel();
    @Mock
    private HorseRepository horseRepository;
    @Mock
    private HorseApplicationMapper horseApplicationMapper;
    @Mock
    private FindOneHorseByUuidUseCase findOneHorseByUuidUseCase;
    @Spy
    @InjectMocks
    private UpdateHorseUseCase updateHorseUseCase;

    @Test
    public void horseNotFound() {
        HorseNotFoundException exception = new HorseNotFoundException(UUID.randomUUID());

        doThrow(exception)
                .when(findOneHorseByUuidUseCase)
                .execute(any());

        assertThrows(HorseNotFoundException.class, () -> updateHorseUseCase.execute(updateHorseDTO));
    }

    @Test
    public void horseIsInScheduleMatchAndIntentToTemporallyInactivate() {

        doReturn(horse)
                .when(findOneHorseByUuidUseCase)
                .execute(any());

        doReturn(true)
                .when(horseRepository)
                .isHorseInScheduledMatch(any());

        assertThrows(CannotTransitionHorseStateException.class, () ->
                updateHorseUseCase.execute(temporallyInactiveUpdateHorseDTO));
    }

    @Test
    public void horseUpdateSuccessfully() {

        doReturn(horse)
                .when(findOneHorseByUuidUseCase)
                .execute(any());

        doReturn(true)
                .when(horseRepository)
                .isHorseInScheduledMatch(any());

        doReturn(updateHorseEntityDTO)
                .when(horseApplicationMapper)
                .toUpdateEntityDTO(any());

        doReturn(horse)
                .when(horseRepository)
                .update(any());

        assertEquals(horse, updateHorseUseCase.execute(updateHorseDTO));

    }
}
