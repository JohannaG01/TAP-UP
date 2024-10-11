package com.johannag.tapup.horses.domain.models;

import com.johannag.tapup.globals.domain.models.SexModel;
import com.johannag.tapup.horseRaces.domain.models.ParticipantModel;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@Data
@Builder(builderClassName = "Builder")
public class HorseModel{
    private UUID uuid;
    private List<ParticipantModel> participations;
    private String code;
    private String name;
    private String breed;
    private LocalDate birthDate;
    private SexModel sex;
    private String color;
    private HorseModelState state;
}