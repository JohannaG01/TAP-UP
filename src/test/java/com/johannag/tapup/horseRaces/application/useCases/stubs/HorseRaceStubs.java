package com.johannag.tapup.horseRaces.application.useCases.stubs;

import com.johannag.tapup.globals.application.utils.DateTimeUtils;
import com.johannag.tapup.horseRaces.application.dtos.CreateHorseRaceDTO;
import com.johannag.tapup.horseRaces.application.dtos.FindHorseRacesDTO;
import com.johannag.tapup.horseRaces.application.dtos.SubmitHorseRaceResultsDTO;
import com.johannag.tapup.horseRaces.application.dtos.UpdateHorseRaceDTO;
import com.johannag.tapup.horseRaces.domain.UpdateHorseRaceEntityDTO;
import com.johannag.tapup.horseRaces.domain.dtos.CreateHorseRaceEntityDTO;
import com.johannag.tapup.horseRaces.domain.dtos.FindHorseRaceEntitiesDTO;
import com.johannag.tapup.horseRaces.domain.dtos.SubmitHorseRaceResultsForEntityDTO;
import com.johannag.tapup.horseRaces.domain.models.HorseRaceModel;
import com.johannag.tapup.horseRaces.domain.models.HorseRaceModelState;
import com.johannag.tapup.horseRaces.domain.models.ParticipantModel;
import com.johannag.tapup.horses.domain.models.HorseModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HorseRaceStubs {

    public static HorseRaceModel horseRaceModel(HorseRaceModelState state) {
        return HorseRaceModel.builder()
                .uuid(UUID.randomUUID())
                .participants(new ArrayList<>())
                .startTime(DateTimeUtils.nowAsLocalDateTime())
                .endTime(DateTimeUtils.nowAsLocalDateTime())
                .state(state)
                .build();
    }

    public static Page<HorseRaceModel> horseRaceModelPage(){
        Pageable pageable = Pageable.ofSize(10);
        return new PageImpl<>(List.of(horseRaceModel(HorseRaceModelState.FINISHED)),pageable,5);
    }

    public static FindHorseRacesDTO findHorseRacesDTO() {
        return FindHorseRacesDTO.builder()
                .page(0)
                .size(10)
                .build();
    }

    public static FindHorseRaceEntitiesDTO findHorseRaceEntitiesDTO() {
        return FindHorseRaceEntitiesDTO.builder()
                .page(0)
                .size(10)
                .build();
    }

    public static CreateHorseRaceDTO createHorseRaceDTO() {
        return CreateHorseRaceDTO.builder()
                .startTime(DateTimeUtils.nowAsLocalDateTime())
                .horsesUuids(List.of(UUID.randomUUID()))
                .build();
    }

    public static CreateHorseRaceEntityDTO createHorseRaceEntityDTO() {
        return CreateHorseRaceEntityDTO.builder()
                .uuid(UUID.randomUUID())
                .startTime(DateTimeUtils.nowAsLocalDateTime())
                .participants(List.of())
                .state(HorseRaceModelState.FINISHED)
                .build();
    }

    public static UpdateHorseRaceDTO updateHorseRaceDTO() {
        return UpdateHorseRaceDTO.builder()
                .horseRaceUuid(UUID.randomUUID())
                .startTime(DateTimeUtils.nowAsLocalDateTime())
                .build();
    }

    public static UpdateHorseRaceEntityDTO updateHorseRaceEntityDTO() {
        return UpdateHorseRaceEntityDTO.builder()
                .raceUuid(UUID.randomUUID())
                .startTime(DateTimeUtils.nowAsLocalDateTime())
                .build();
    }

    public static SubmitHorseRaceResultsDTO submitHorseRaceResultsDTO(){
        return SubmitHorseRaceResultsDTO.builder()
                .horseRaceUuid(UUID.randomUUID())
                .endTime(DateTimeUtils.nowAsLocalDateTime())
                .participants(List.of(submitHorseRaceResultsDTOParticipant()))
                .build();
    }

    public static SubmitHorseRaceResultsDTO.Participant submitHorseRaceResultsDTOParticipant(){
        return SubmitHorseRaceResultsDTO.Participant.builder()
                .uuid(UUID.randomUUID())
                .placement(1)
                .time(LocalTime.now())
                .build();
    }

    public static SubmitHorseRaceResultsForEntityDTO submitHorseRaceResultsForEntityDTO(){
        return SubmitHorseRaceResultsForEntityDTO.builder()
                .horseRaceUuid(UUID.randomUUID())
                .endTime(DateTimeUtils.nowAsLocalDateTime())
                .participants(List.of())
                .build();
    }

    public static ParticipantModel participantModel(){
        return ParticipantModel.builder()
                .uuid(UUID.randomUUID())
                .horse(HorseModel.builder().build())
                .horseRace(HorseRaceModel.builder().build())
                .bets(List.of())
                .placement(1)
                .time(LocalTime.now())
                .build();
    }
}
