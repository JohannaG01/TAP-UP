package com.johannag.tapup.horseRaces.presentation.dtos.requests;

import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderClassName = "Builder")
public class SubmitHorseRaceResultsRequestDTO {

    @PastOrPresent
    private LocalDateTime endTime;

    @NotEmpty
    private List<Participant> participants;

    @AssertFalse(message = "Placements must be consecutive starting from one and times must be ascendant")
    private boolean isInvalidParticipantsOrder() {
        if (participants == null || participants.isEmpty()) {
            return false;
        }

        int expectedPlacement = 1;
        LocalTime previousTime = null;

        for (Participant participant : participants) {
            if (participant.getPlacement() != expectedPlacement) {
                return true;
            }

            if (previousTime != null && participant.getTime().isBefore(previousTime)) {
                return true;
            }

            previousTime = participant.getTime();
            expectedPlacement++;
        }

        return false;
    }

    @AssertFalse(message = "Participants must be unique")
    private boolean isParticipantsNotUnique() {
        if (participants == null || participants.isEmpty()) {
            return false;
        }

        Set<UUID> partisipantsUuidAsSet = participants.stream()
                .map(Participant::getUuid)
                .collect(Collectors.toSet());

        return partisipantsUuidAsSet.size() != participants.size();
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @lombok.Builder(builderClassName = "Builder")
    public static class Participant {
        @NotNull
        private UUID uuid;

        @NotNull
        private Integer placement;

        @NotNull
        private LocalTime time;
    }
}
