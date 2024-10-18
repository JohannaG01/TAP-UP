package com.johannag.tapup.bets.infrastructure.db.entities;

import java.util.Arrays;
import java.util.List;

public enum BetEntityState {
    PENDING,
    PAID,
    REFUND,
    LOST;

    public static List<BetEntityState> valuesAsList(){
        return Arrays.stream(values()).toList();
    }
}
