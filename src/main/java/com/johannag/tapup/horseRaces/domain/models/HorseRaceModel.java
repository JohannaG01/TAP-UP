package com.johannag.tapup.horseRaces.domain.models;

import com.johannag.tapup.globals.application.utils.DateTimeUtils;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public boolean hasAlreadyStarted(){
        return this.startTime.isBefore(DateTimeUtils.nowAsLocalDateTime());
    }

    public List<UUID> checkForMissingParticipants(Collection<UUID> participantUuidsToValidate) {
        Set<UUID> participantUuids = this.participants.stream()
                .map(ParticipantModel::getUuid)
                .collect(Collectors.toSet());

        return participantUuidsToValidate.stream()
                .filter(uuid -> !participantUuids.contains(uuid))
                .collect(Collectors.toList());
    }
}