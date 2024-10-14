package com.johannag.tapup.horseRaces.domain.dtos;

import com.johannag.tapup.horseRaces.domain.models.HorseRaceModelState;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Value
@Builder(builderClassName = "Builder")
public class CreateHorseRaceEntityDTO {

    UUID uuid;
    List<CreateParticipantEntityDTO> participants;
    LocalDateTime startTime;
    HorseRaceModelState state;

    public List<UUID> getHorseUuids(){
        return this.participants.stream()
                .map(CreateParticipantEntityDTO::getHorseUuid)
                .toList();
    }

}
