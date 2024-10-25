package com.johannag.tapup.horseRaces.application.useCases;

import com.johannag.tapup.horseRaces.application.dtos.FindHorseRacesDTO;
import com.johannag.tapup.horseRaces.application.mappers.HorseRaceApplicationMapper;
import com.johannag.tapup.horseRaces.application.useCases.stubs.HorseRaceStubs;
import com.johannag.tapup.horseRaces.domain.dtos.FindHorseRaceEntitiesDTO;
import com.johannag.tapup.horseRaces.domain.models.HorseRaceModel;
import com.johannag.tapup.horseRaces.infrastructure.db.adapters.HorseRaceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class FindHorseRacesUseCaseTests {

    @Mock
    private HorseRaceRepository horseRaceRepository;

    @Mock
    private HorseRaceApplicationMapper horseRaceApplicationMapper;

    @Spy
    @InjectMocks
    private FindHorseRacesUseCase findHorseRacesUseCase;

    private final Page<HorseRaceModel> horseRaces = HorseRaceStubs.horseRaceModelPage();
    private final FindHorseRacesDTO findHorseRacesDTO = HorseRaceStubs.findHorseRacesDTO();
    private final FindHorseRaceEntitiesDTO findHorseEntitiesDTO = HorseRaceStubs.findHorseRaceEntitiesDTO();

    @Test
    public void horseRacesFoundSuccessfully(){

        doReturn(findHorseEntitiesDTO)
                .when(horseRaceApplicationMapper)
                .toFindEntitiesDTO(any());

        doReturn(horseRaces)
                .when(horseRaceRepository)
                .findAll(any());

        assertEquals(horseRaces, findHorseRacesUseCase.execute(findHorseRacesDTO));
    }
}
