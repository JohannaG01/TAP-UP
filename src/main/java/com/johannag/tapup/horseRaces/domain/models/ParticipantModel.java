package com.johannag.tapup.horseRaces.domain.models;

import com.johannag.tapup.bets.domain.models.BetModel;
import com.johannag.tapup.horses.domain.models.HorseModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderClassName = "Builder")
public class ParticipantModel {
    private UUID uuid;
    private HorseModel horse;
    private HorseRaceModel horseRace;
    private List<BetModel> bets;
    private Integer placement;
    private LocalTime time;

    public UUID getHorseUuid() {
        return horse.getUuid();
    }
}