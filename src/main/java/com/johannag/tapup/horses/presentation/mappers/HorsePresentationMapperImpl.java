package com.johannag.tapup.horses.presentation.mappers;

import com.johannag.tapup.auth.presentation.mappers.AuthTokenPresentationMapper;
import com.johannag.tapup.horses.domain.models.HorseModel;
import com.johannag.tapup.horses.presentation.dtos.HorseStateDTO;
import com.johannag.tapup.horses.presentation.dtos.responses.HorseResponseDTO;
import com.johannag.tapup.users.domain.models.UserModel;
import com.johannag.tapup.users.presentation.dtos.responses.UserResponseDTO;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import static com.johannag.tapup.globals.application.utils.ModelMapperUtils.builderTypeMapper;

@Component
public class HorsePresentationMapperImpl implements HorsePresentationMapper {

    private final TypeMap<HorseModel, HorseResponseDTO.Builder> horseResponseDTOMapper;

    public HorsePresentationMapperImpl() {
        horseResponseDTOMapper = builderTypeMapper(HorseModel.class, HorseResponseDTO.Builder.class);
    }

    @Override
    public HorseResponseDTO toHorseResponseDTO(HorseModel horse) {
        return horseResponseDTOMapper
                .map(horse)
                .state(HorseStateDTO.valueOf(horse.getState().name()))
                .build();
    }
}
