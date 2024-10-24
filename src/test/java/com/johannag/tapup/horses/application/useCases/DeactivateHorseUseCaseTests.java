package com.johannag.tapup.horses.application.useCases;

import com.johannag.tapup.horses.application.exceptions.HorseNotFoundException;
import com.johannag.tapup.horses.application.useCases.stubs.HorseStubs;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class DeactivateHorseUseCaseTests {

    private final HorseModel horse = HorseStubs.horseModel();
    @Mock
    private HorseRepository horseRepository;
    @Mock
    private FindOneHorseByUuidUseCase findOneHorseByUuidUseCase;
    @Spy
    @InjectMocks
    private DeactivateHorseUseCase deactivateHorseUseCase;

    @Test
    public void horseNotFound() {
        HorseNotFoundException exception = new HorseNotFoundException(UUID.randomUUID());

        doThrow(exception)
                .when(findOneHorseByUuidUseCase)
                .execute(any());

        assertThrows(HorseNotFoundException.class, () -> deactivateHorseUseCase.execute(UUID.randomUUID()));
    }

    @Test
    public void horseDeactivatedSuccessfully() {

        doReturn(horse)
                .when(findOneHorseByUuidUseCase)
                .execute(any());

        doReturn(horse)
                .when(horseRepository)
                .deactivateByUuid(any());

        assertEquals(horse, deactivateHorseUseCase.execute(UUID.randomUUID()));
    }
}
