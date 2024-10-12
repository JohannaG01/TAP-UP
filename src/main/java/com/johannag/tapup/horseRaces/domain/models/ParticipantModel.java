package com.johannag.tapup.horseRaces.domain.models;

import com.johannag.tapup.horses.domain.models.HorseModel;
import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;
import java.util.UUID;

@Data
@Builder(builderClassName = "Builder")
public class ParticipantModel {
    private UUID uuid;
    private HorseModel horse;
    private HorseRaceModel horseRace;
    private Integer placement;
    private LocalTime time;
}