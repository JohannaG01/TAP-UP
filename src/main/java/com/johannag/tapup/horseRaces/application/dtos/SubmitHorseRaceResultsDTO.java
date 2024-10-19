package com.johannag.tapup.horseRaces.application.dtos;

import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderClassName = "Builder")
public class SubmitHorseRaceResultsDTO {
     private UUID horseRaceUuid;
     private LocalDateTime endTime;
     private List<Participant> participants;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @lombok.Builder(builderClassName = "Builder")
    public static class Participant {
        private UUID uuid;
        private int placement;
        private LocalTime time;
    }
}
