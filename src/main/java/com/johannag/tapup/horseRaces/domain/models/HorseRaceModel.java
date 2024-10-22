package com.johannag.tapup.horseRaces.domain.models;

import com.johannag.tapup.globals.application.utils.DateTimeUtils;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
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

    public boolean isFinished() {
        return this.state == HorseRaceModelState.FINISHED;
    }

    public boolean hasAlreadyStarted() {
        return this.startTime.isBefore(DateTimeUtils.nowAsLocalDateTime());
    }

    /**
     * Checks for participants whose UUIDs are missing from the current list of participants.
     *
     * @param participantUuidsToValidate a collection of UUIDs representing the participants to validate
     * @return a list of UUIDs that are missing from the current participants
     */
    public List<UUID> checkForMissingParticipants(Collection<UUID> participantUuidsToValidate) {
        Set<UUID> participantUuids = this.participants.stream()
                .map(ParticipantModel::getUuid)
                .collect(Collectors.toSet());

        return participantUuidsToValidate.stream()
                .filter(uuid -> !participantUuids.contains(uuid))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves the horse uuid who is marked as the winner.
     *
     * @return the {@link UUID} representing the uuid of the winning horse
     * @throws NoSuchElementException if no horse is found marked as the winner
     */
    public UUID getWinnerHorseUuid() throws NoSuchElementException {
        return this.participants.stream()
                .filter(ParticipantModel::isWinner)
                .map(ParticipantModel::getHorseUuid)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No winner found among participants"));
    }
}