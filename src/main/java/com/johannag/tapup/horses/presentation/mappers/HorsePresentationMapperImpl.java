package com.johannag.tapup.horses.presentation.mappers;

import com.johannag.tapup.horses.domain.models.HorseModel;
import com.johannag.tapup.horses.presentation.dtos.responses.HorseResponseDTO;
import org.modelmapper.TypeMap;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import static com.johannag.tapup.globals.application.utils.ModelMapperUtils.builderTypeMapper;

@Component
public class HorsePresentationMapperImpl implements HorsePresentationMapper {

    private final TypeMap<HorseModel, HorseResponseDTO.Builder> responseDTOMapper;

    public HorsePresentationMapperImpl() {
        responseDTOMapper = builderTypeMapper(HorseModel.class, HorseResponseDTO.Builder.class);
    }

    @Override
    public HorseResponseDTO toResponseDTO(HorseModel horse) {
        return responseDTOMapper
                .map(horse)
                .build();
    }

    @Override
    public Page<HorseResponseDTO> toResponseDTO(Page<HorseModel> horses) {
        return horses.map(this::toResponseDTO);
    }
}
