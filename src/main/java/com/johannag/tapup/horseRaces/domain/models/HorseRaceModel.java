package com.johannag.tapup.horseRaces.domain.models;

import lombok.Builder;
import lombok.Data;

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

    public List<UUID> getHorseUuids() {
        return this.participants.stream()
                .map(ParticipantModel::getHorseUuid)
                .toList();
    }

    public boolean isScheduled() {
        return this.state == HorseRaceModelState.SCHEDULED;
    }
}