package com.johannag.tapup.horses.infrastructure.db.entities;

import java.util.List;

public enum HorseEntityState {
    ACTIVE,
    INACTIVE,
    TEMPORALLY_INACTIVE;

    public static List<HorseEntityState> existenceStates() {
        return List.of(ACTIVE, TEMPORALLY_INACTIVE);
    }
}
