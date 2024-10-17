package com.johannag.tapup.bets.domain.models;

import com.johannag.tapup.horseRaces.domain.models.ParticipantModel;
import com.johannag.tapup.users.domain.models.UserModel;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder(builderClassName = "Builder")
public class BetModel {
    private final UUID uuid;
    private final UserModel user;
    private final ParticipantModel participant;
    private final BigDecimal amount;
    private final BetModelState state;
}