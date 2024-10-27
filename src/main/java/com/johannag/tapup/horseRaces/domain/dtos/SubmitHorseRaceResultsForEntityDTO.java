package com.johannag.tapup.horseRaces.domain.dtos;

import lombok.Value;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Value
@lombok.Builder(builderClassName = "Builder")
public class SubmitHorseRaceResultsForEntityDTO {
    UUID horseRaceUuid;
    LocalDateTime endTime;
    List<Participant> participants;

    public int getPlacementForParticipantUuid(UUID participantUuid) {
        return participants.stream()
                .filter(p -> p.getUuid().equals(participantUuid))
                .map(Participant::getPlacement)
                .findAny()
                .orElseThrow();
    }

    public LocalTime getTimeForParticipantUuid(UUID participantUuid) {
        return participants.stream()
                .filter(p -> p.getUuid().equals(participantUuid))
                .map(Participant::getTime)
                .findAny()
                .orElseThrow();
    }

    @Value
    @lombok.Builder(builderClassName = "Builder")
    public static class Participant {
        UUID uuid;
        int placement;
        LocalTime time;
    }
}
