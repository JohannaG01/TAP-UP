package com.johannag.tapup.horses.application.useCases;

import com.johannag.tapup.auth.application.services.AuthenticationService;
import com.johannag.tapup.horses.application.dtos.FindHorsesDTO;
import com.johannag.tapup.horses.application.mappers.HorseApplicationMapper;
import com.johannag.tapup.horses.application.useCases.stubs.HorseStubs;
import com.johannag.tapup.horses.domain.dtos.FindHorseEntitiesDTO;
import com.johannag.tapup.horses.domain.models.HorseModel;
import com.johannag.tapup.horses.domain.models.HorseModelState;
import com.johannag.tapup.horses.infrastructure.db.adapters.HorseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class FindHorsesUseCaseTests {

    private final FindHorsesDTO findHorsesDTO = HorseStubs.findHorsesDTO();
    private final FindHorseEntitiesDTO.Builder findHorseEntitiesDTOBuilder = HorseStubs.findHorseEntitiesDTO();
    private final Page<HorseModel> horseModelPage = HorseStubs.horseModelPage();
    @Mock
    private HorseRepository horseRepository;
    @Mock
    private AuthenticationService authenticationService;
    @Mock
    private HorseApplicationMapper horseApplicationMapper;
    @Spy
    @InjectMocks
    private FindHorsesUseCase findHorsesUseCase;

    @Test
    public void horsesFoundSuccessfullyForRegularUser() {

        doReturn(findHorseEntitiesDTOBuilder)
                .when(horseApplicationMapper)
                .toFindEntitiesDTO(any());

        doReturn(false)
                .when(authenticationService)
                .isAdminUser();

        doReturn(horseModelPage)
                .when(horseRepository)
                .findAll(any());

        assertEquals(horseModelPage, findHorsesUseCase.execute(findHorsesDTO));

        ArgumentCaptor<FindHorseEntitiesDTO> captor = ArgumentCaptor.forClass(FindHorseEntitiesDTO.class);
        verify(horseRepository).findAll(captor.capture());

        FindHorseEntitiesDTO capturedDTO = captor.getValue();
        assertEquals(List.of(HorseModelState.ACTIVE), capturedDTO.getStates());
    }

    @Test
    public void horsesFoundSuccessfullyForAdminUser() {

        doReturn(findHorseEntitiesDTOBuilder)
                .when(horseApplicationMapper)
                .toFindEntitiesDTO(any());

        doReturn(true)
                .when(authenticationService)
                .isAdminUser();

        doReturn(horseModelPage)
                .when(horseRepository)
                .findAll(any());

        assertEquals(horseModelPage, findHorsesUseCase.execute(findHorsesDTO));

        ArgumentCaptor<FindHorseEntitiesDTO> captor = ArgumentCaptor.forClass(FindHorseEntitiesDTO.class);
        verify(horseRepository).findAll(captor.capture());

        FindHorseEntitiesDTO capturedDTO = captor.getValue();
        assertNull(capturedDTO.getStates());
    }
}
