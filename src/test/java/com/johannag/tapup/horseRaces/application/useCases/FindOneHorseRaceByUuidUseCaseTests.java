package com.johannag.tapup.horseRaces.application.useCases;

import com.johannag.tapup.horseRaces.application.exceptions.HorseRaceNotFoundException;
import com.johannag.tapup.horseRaces.application.useCases.stubs.HorseRaceStubs;
import com.johannag.tapup.horseRaces.domain.models.HorseRaceModel;
import com.johannag.tapup.horseRaces.domain.models.HorseRaceModelState;
import com.johannag.tapup.horseRaces.infrastructure.db.adapters.HorseRaceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class FindOneHorseRaceByUuidUseCaseTests {

    private final HorseRaceModel horseRace = HorseRaceStubs.horseRaceModel(HorseRaceModelState.SCHEDULED);
    @Mock
    private HorseRaceRepository horseRaceRepository;
    @Spy
    @InjectMocks
    private FindOneHorseRaceByUuidUseCase findOneHorseRaceByUuidUseCase;

    @Test
    public void horseNotFound() {
        HorseRaceNotFoundException exception = new HorseRaceNotFoundException(UUID.randomUUID());

        doThrow(exception)
                .when(horseRaceRepository)
                .findOneMaybeByParticipantUuid(UUID.randomUUID());

        assertThrows(HorseRaceNotFoundException.class, () -> findOneHorseRaceByUuidUseCase.execute(UUID.randomUUID()));
    }

    @Test
    public void horseRaceFoundSuccessfully() {

        doReturn(Optional.of(horseRace))
                .when(horseRaceRepository)
                .findOneMaybeByUuid(any());

        assertEquals(horseRace, findOneHorseRaceByUuidUseCase.execute(UUID.randomUUID()));
    }

}
