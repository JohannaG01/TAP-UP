package com.johannag.tapup.horseRaces.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder(builderClassName = "Builder")
public class HorseRaceModel implements Serializable {
    private UUID uuid;
    private List<ParticipantModel> participants;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private HorseRaceModelState state;
}