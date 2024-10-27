package com.johannag.tapup.horseRaces.domain.dtos;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder(builderClassName = "Builder")
public class CreateParticipantEntityDTO {
    UUID uuid;
    UUID horseUuid;
}
