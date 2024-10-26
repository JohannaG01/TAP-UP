package com.johannag.tapup.horseRaces.application.useCases;

import com.johannag.tapup.horseRaces.application.dtos.UpdateHorseRaceDTO;
import com.johannag.tapup.horseRaces.application.exceptions.HorseRaceNotFoundException;
import com.johannag.tapup.horseRaces.application.exceptions.InvalidHorseRaceStateException;
import com.johannag.tapup.horseRaces.application.mappers.HorseRaceApplicationMapper;
import com.johannag.tapup.horseRaces.application.useCases.stubs.HorseRaceStubs;
import com.johannag.tapup.horseRaces.domain.UpdateHorseRaceEntityDTO;
import com.johannag.tapup.horseRaces.domain.models.HorseRaceModel;
import com.johannag.tapup.horseRaces.domain.models.HorseRaceModelState;
import com.johannag.tapup.horseRaces.infrastructure.db.adapters.HorseRaceRepository;
import com.johannag.tapup.horses.application.exceptions.HorseNotAvailableException;
import com.johannag.tapup.horses.application.services.HorseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UpdateHorseRaceUseCaseTests {

    private final UpdateHorseRaceDTO updateHorseRaceDTO = HorseRaceStubs.updateHorseRaceDTO();
    private final UpdateHorseRaceEntityDTO updateHorseRaceEntityDTO = HorseRaceStubs.updateHorseRaceEntityDTO();
    private final HorseRaceModel finishedHorseRace = HorseRaceStubs.horseRaceModel(HorseRaceModelState.FINISHED);
    private final HorseRaceModel scheduledHorseRace = HorseRaceStubs.horseRaceModel(HorseRaceModelState.SCHEDULED);
    @Mock
    private HorseRaceRepository horseRaceRepository;
    @Mock
    private HorseRaceApplicationMapper horseRaceApplicationMapper;
    @Mock
    private HorseService horseService;
    @Mock
    private FindOneHorseRaceByUuidUseCase findOneHorseRaceByUuidUseCase;
    @Spy
    @InjectMocks
    private UpdateHorseRaceUseCase updateHorseRaceUseCase;

    @Test
    public void horseRaceNotFound() {
        HorseRaceNotFoundException exception = new HorseRaceNotFoundException(UUID.randomUUID());

        doThrow(exception)
                .when(findOneHorseRaceByUuidUseCase)
                .execute(any());

        assertThrows(HorseRaceNotFoundException.class, () -> updateHorseRaceUseCase.execute(updateHorseRaceDTO));
    }

    @Test
    public void horseRaceIsNotScheduled() {

        doReturn(finishedHorseRace)
                .when(findOneHorseRaceByUuidUseCase)
                .execute(any());

        assertThrows(InvalidHorseRaceStateException.class, () -> updateHorseRaceUseCase.execute(updateHorseRaceDTO));
    }

    @Test
    public void horsesAreNoAvailableForNewStartTime() {
        HorseNotAvailableException exception =
                new HorseNotAvailableException(List.of(UUID.randomUUID()), 0, updateHorseRaceDTO.getStartTime());

        doReturn(scheduledHorseRace)
                .when(findOneHorseRaceByUuidUseCase)
                .execute(any());

        doThrow(exception)
                .when(horseService)
                .validateHorsesAvailability(any(), any(), any());

        assertThrows(HorseNotAvailableException.class, () -> updateHorseRaceUseCase.execute(updateHorseRaceDTO));
    }

    @Test
    public void horseRaceUpdatedSuccessfully() {

        doReturn(scheduledHorseRace)
                .when(findOneHorseRaceByUuidUseCase)
                .execute(any());

        doNothing()
                .when(horseService)
                .validateHorsesAvailability(any(), any(), any());

        doReturn(updateHorseRaceEntityDTO)
                .when(horseRaceApplicationMapper)
                .toUpdateEntityDTO(any());

        doReturn(scheduledHorseRace)
                .when(horseRaceRepository)
                .update(any());

        assertEquals(scheduledHorseRace, updateHorseRaceUseCase.execute(updateHorseRaceDTO));
    }
}
