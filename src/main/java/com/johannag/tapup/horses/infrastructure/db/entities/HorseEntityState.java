package com.johannag.tapup.horses.infrastructure.db.entities;

import java.util.List;

public enum HorseEntityState {
    ACTIVE,
    INACTIVE,
    TEMPORARILY_INACTIVE;

    public static List<HorseEntityState> existenceStates() {
        return List.of(ACTIVE, TEMPORARILY_INACTIVE);
    }
}
