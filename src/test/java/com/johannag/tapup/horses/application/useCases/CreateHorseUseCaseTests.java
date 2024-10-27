package com.johannag.tapup.horses.application.useCases;

import com.johannag.tapup.horses.application.dtos.CreateHorseDTO;
import com.johannag.tapup.horses.application.exceptions.HorseAlreadyExistsException;
import com.johannag.tapup.horses.application.mappers.HorseApplicationMapper;
import com.johannag.tapup.horses.application.useCases.stubs.HorseStubs;
import com.johannag.tapup.horses.domain.dtos.CreateHorseEntityDTO;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CreateHorseUseCaseTests {

    private final CreateHorseDTO createHorseDTO = HorseStubs.createHorseDTO();
    private final CreateHorseEntityDTO createHorseEntityDTO = HorseStubs.createHorseEntityDTO();
    private final HorseModel horse = HorseStubs.horseModel();
    @Mock
    private HorseRepository horseRepository;
    @Mock
    private HorseApplicationMapper horseApplicationMapper;
    @Spy
    @InjectMocks
    private CreateHorseUseCase createHorseUseCase;

    @Test
    public void horseAlreadyExists() {

        doReturn(true)
                .when(horseRepository)
                .existsHorseByCode(any());

        assertThrows(HorseAlreadyExistsException.class, () -> createHorseUseCase.execute(createHorseDTO));
    }

    @Test
    public void horseCreatedSuccessfully() {

        doReturn(false)
                .when(horseRepository)
                .existsHorseByCode(any());

        doReturn(createHorseEntityDTO)
                .when(horseApplicationMapper)
                .toCreateEntityDTO(any());

        doReturn(horse)
                .when(horseRepository)
                .upsert(any());

        assertEquals(horse, createHorseUseCase.execute(createHorseDTO));
    }
}
