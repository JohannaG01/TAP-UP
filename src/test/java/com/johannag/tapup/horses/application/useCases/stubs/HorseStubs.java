package com.johannag.tapup.horses.application.useCases.stubs;

import com.johannag.tapup.globals.application.utils.DateTimeUtils;
import com.johannag.tapup.globals.domain.models.SexModel;
import com.johannag.tapup.horses.application.dtos.CreateHorseDTO;
import com.johannag.tapup.horses.application.dtos.FindHorsesDTO;
import com.johannag.tapup.horses.application.dtos.UpdateHorseDTO;
import com.johannag.tapup.horses.domain.dtos.CreateHorseEntityDTO;
import com.johannag.tapup.horses.domain.dtos.FindHorseEntitiesDTO;
import com.johannag.tapup.horses.domain.dtos.UpdateHorseEntityDTO;
import com.johannag.tapup.horses.domain.models.HorseModel;
import com.johannag.tapup.horses.domain.models.HorseModelState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HorseStubs {

    public static FindHorsesDTO findHorsesDTO() {
        return FindHorsesDTO.builder()
                .page(0)
                .size(10)
                .build();
    }

    public static FindHorseEntitiesDTO.Builder findHorseEntitiesDTO() {
        return FindHorseEntitiesDTO.builder()
                .page(0)
                .size(10);
    }

    public static HorseModel horseModel() {
        return HorseModel.builder()
                .uuid(UUID.randomUUID())
                .participations(new ArrayList<>())
                .code("1234")
                .name("Pancho")
                .breed("Arabic")
                .birthDate(DateTimeUtils.nowAsLocalDateTime().toLocalDate())
                .sex(SexModel.MALE)
                .color("Black")
                .state(HorseModelState.ACTIVE)
                .build();
    }

    public static Page<HorseModel> horseModelPage() {
        Pageable pageable = Pageable.ofSize(10);
        return new PageImpl<>(List.of(horseModel()), pageable, 5);
    }

    public static CreateHorseDTO createHorseDTO() {
        return CreateHorseDTO.builder()
                .code("1234")
                .name("Juancho")
                .breed("Arabic")
                .birthDate(DateTimeUtils.nowAsLocalDateTime().toLocalDate())
                .sex(SexModel.MALE)
                .color("Black")
                .build();
    }

    public static CreateHorseEntityDTO createHorseEntityDTO() {
        return CreateHorseEntityDTO.builder()
                .code("1234")
                .name("Juancho")
                .breed("Arabic")
                .birthDate(DateTimeUtils.nowAsLocalDateTime().toLocalDate())
                .sex(SexModel.MALE)
                .color("Black")
                .state(HorseModelState.ACTIVE)
                .build();
    }

    public static UpdateHorseDTO updateHorseDTO(HorseModelState state) {
        return UpdateHorseDTO.builder()
                .uuid(UUID.randomUUID())
                .name("Juancho")
                .breed("Arabic")
                .birthDate(DateTimeUtils.nowAsLocalDateTime().toLocalDate())
                .sex(SexModel.MALE)
                .color("Black")
                .state(state)
                .build();
    }

    public static UpdateHorseEntityDTO updateHorseEntityDTO() {
        return UpdateHorseEntityDTO.builder()
                .uuid(UUID.randomUUID())
                .name("Juancho")
                .breed("Arabic")
                .birthDate(DateTimeUtils.nowAsLocalDateTime().toLocalDate())
                .sex(SexModel.MALE)
                .color("Black")
                .state(HorseModelState.ACTIVE)
                .build();
    }
}
