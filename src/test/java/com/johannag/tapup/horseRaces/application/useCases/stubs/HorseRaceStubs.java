package com.johannag.tapup.horseRaces.application.useCases.stubs;

import com.johannag.tapup.globals.application.utils.DateTimeUtils;
import com.johannag.tapup.horseRaces.domain.models.HorseRaceModel;
import com.johannag.tapup.horseRaces.domain.models.HorseRaceModelState;
import com.johannag.tapup.horseRaces.domain.models.ParticipantModel;

import java.util.ArrayList;
import java.util.UUID;

public class HorseRaceStubs {

    public static HorseRaceModel horseRaceStub(HorseRaceModelState state) {
        return HorseRaceModel.builder()
                .uuid(UUID.randomUUID())
                .participants(new ArrayList<>())
                .startTime(DateTimeUtils.nowAsLocalDateTime())
                .endTime(DateTimeUtils.nowAsLocalDateTime())
                .state(state)
                .build();
    }
}
