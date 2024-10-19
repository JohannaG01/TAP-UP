package com.johannag.tapup.horseRaces.application.services;

import com.johannag.tapup.bets.application.exceptions.UnexpectedPaymentException;
import com.johannag.tapup.horseRaces.application.dtos.CreateHorseRaceDTO;
import com.johannag.tapup.horseRaces.application.dtos.FindHorseRacesDTO;
import com.johannag.tapup.horseRaces.application.dtos.UpdateHorseRaceDTO;
import com.johannag.tapup.horseRaces.application.exceptions.HorseRaceNotFoundException;
import com.johannag.tapup.horseRaces.application.exceptions.InvalidHorseRaceStateException;
import com.johannag.tapup.horseRaces.application.exceptions.ParticipantNotFoundException;
import com.johannag.tapup.horseRaces.application.useCases.*;
import com.johannag.tapup.horseRaces.application.dtos.SubmitHorseRaceResultsDTO;
import com.johannag.tapup.horseRaces.domain.models.HorseRaceModel;
import com.johannag.tapup.horses.application.exceptions.HorseNotAvailableException;
import com.johannag.tapup.horses.application.exceptions.HorseNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class HorseRaceServiceImpl implements HorseRaceService {

    private final CreateHorseRaceUseCase createHorseRaceUseCase;
    private final UpdateHorseRaceUseCase updateHorseRaceUseCase;
    private final FindOneHorseRaceByUuidUseCase findOneHorseRaceByUuidUseCase;
    private final FindHorseRacesUseCase findHorseRacesUseCase;
    private final FindOneHorseRaceByParticipantUuidUseCase findOneHorseRaceByParticipantUuidUseCase;
    private final SubmitHorseRaceResultsUseCase submitHorseRaceResultsUseCase;

    @Override
    public HorseRaceModel create(CreateHorseRaceDTO dto) throws HorseNotAvailableException, HorseNotFoundException {
        return createHorseRaceUseCase.execute(dto);
    }

    @Override
    public HorseRaceModel update(UpdateHorseRaceDTO dto) throws HorseRaceNotFoundException,
            InvalidHorseRaceStateException, HorseNotAvailableException {
        return updateHorseRaceUseCase.execute(dto);
    }

    @Override
    public HorseRaceModel findOneByUuid(UUID uuid) throws HorseRaceNotFoundException {
        return findOneHorseRaceByUuidUseCase.execute(uuid);
    }

    @Override
    public Page<HorseRaceModel> findAll(FindHorseRacesDTO dto) {
        return findHorseRacesUseCase.execute(dto);
    }

    @Override
    public HorseRaceModel findByParticipantUuid(UUID participantUuid) throws ParticipantNotFoundException {
        return findOneHorseRaceByParticipantUuidUseCase.execute(participantUuid);
    }

    @Override
    public HorseRaceModel submitResults(SubmitHorseRaceResultsDTO dto)
            throws ParticipantNotFoundException, HorseRaceNotFoundException, InvalidHorseRaceStateException, UnexpectedPaymentException {
        return submitHorseRaceResultsUseCase.execute(dto);
    }
}
