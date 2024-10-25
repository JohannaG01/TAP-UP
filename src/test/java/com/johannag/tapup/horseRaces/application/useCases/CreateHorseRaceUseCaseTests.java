package com.johannag.tapup.horseRaces.application.useCases;

import com.johannag.tapup.horseRaces.application.dtos.CreateHorseRaceDTO;
import com.johannag.tapup.horseRaces.application.mappers.HorseRaceApplicationMapper;
import com.johannag.tapup.horseRaces.application.useCases.stubs.HorseRaceStubs;
import com.johannag.tapup.horseRaces.domain.dtos.CreateHorseRaceEntityDTO;
import com.johannag.tapup.horseRaces.domain.models.HorseRaceModel;
import com.johannag.tapup.horseRaces.domain.models.HorseRaceModelState;
import com.johannag.tapup.horseRaces.infrastructure.db.adapters.HorseRaceRepository;
import com.johannag.tapup.horses.application.exceptions.HorseNotAvailableException;
import com.johannag.tapup.horses.application.exceptions.HorseNotFoundException;
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
public class CreateHorseRaceUseCaseTests {

    @Mock
    private HorseRaceRepository horseRaceRepository;

    @Mock
    private HorseRaceApplicationMapper horseRaceApplicationMapper;

    @Mock
    private HorseService horseService;

    @Spy
    @InjectMocks
    private CreateHorseRaceUseCase createHorseRaceUseCase;

    private final CreateHorseRaceDTO createHorseRaceDTO = HorseRaceStubs.createHorseRaceDTO();
    private final CreateHorseRaceEntityDTO createHorseRaceEntityDTO = HorseRaceStubs.createHorseRaceEntityDTO();
    private final HorseRaceModel horseRace = HorseRaceStubs.horseRaceModel(HorseRaceModelState.SCHEDULED);

    @Test
    public void horsesNotFound() {
        HorseNotFoundException exception = new HorseNotFoundException(List.of(UUID.randomUUID()));

        doThrow(exception)
                .when(horseService)
                .validateHorsesAvailability(any(), any(), any());

        assertThrows(HorseNotFoundException.class, () -> createHorseRaceUseCase.execute(createHorseRaceDTO));
    }

    @Test
    public void horsesAreNotAvailable() {
        HorseNotAvailableException exception =
                new HorseNotAvailableException(List.of(UUID.randomUUID()), 1, createHorseRaceDTO.getStartTime());

        doThrow(exception)
                .when(horseService)
                .validateHorsesAvailability(any(), any(), any());

        assertThrows(HorseNotAvailableException.class, () -> createHorseRaceUseCase.execute(createHorseRaceDTO));
    }

    @Test
    public void horseRaceCreatedSuccessfully() {

        doNothing()
                .when(horseService)
                .validateHorsesAvailability(any(), any(), any());

        doReturn(createHorseRaceEntityDTO)
                .when(horseRaceApplicationMapper)
                .toCreateEntityDTO(createHorseRaceDTO);

        doReturn(horseRace)
                .when(horseRaceRepository)
                .create(any());

        assertEquals(horseRace, createHorseRaceUseCase.execute(createHorseRaceDTO));
    }
}
