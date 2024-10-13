package com.johannag.tapup.horses.application.mappers;

import com.johannag.tapup.horses.application.dtos.CreateHorseDTO;
import com.johannag.tapup.horses.application.dtos.FindHorsesDTO;
import com.johannag.tapup.horses.application.dtos.UpdateHorseDTO;
import com.johannag.tapup.horses.domain.dtos.CreateHorseEntityDTO;
import com.johannag.tapup.horses.domain.dtos.UpdateHorseEntityDTO;
import com.johannag.tapup.horses.domain.models.HorseModelState;
import com.johannag.tapup.horses.presentation.dtos.HorseStateDTO;
import com.johannag.tapup.horses.presentation.dtos.query.FindHorsesQuery;
import com.johannag.tapup.horses.presentation.dtos.requests.CreateHorseRequestDTO;
import com.johannag.tapup.horses.presentation.dtos.requests.UpdateHorseRequestDTO;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static com.johannag.tapup.globals.application.utils.ModelMapperUtils.builderTypeMapper;

@Component
public class HorseApplicationMapperImpl implements HorseApplicationMapper {

    private final TypeMap<CreateHorseRequestDTO, CreateHorseDTO.Builder> createDTOMapper;
    private final TypeMap<CreateHorseDTO, CreateHorseEntityDTO.Builder> createEntityDTOMapper;
    private final TypeMap<UpdateHorseRequestDTO, UpdateHorseDTO.Builder> updateDTOMapper;
    private final TypeMap<UpdateHorseDTO, UpdateHorseEntityDTO.Builder> updateEntityDTOMapper;
    private final TypeMap<FindHorsesQuery, FindHorsesDTO.Builder> findDTOMapper;

    public HorseApplicationMapperImpl() {
        createDTOMapper = builderTypeMapper(CreateHorseRequestDTO.class, CreateHorseDTO.Builder.class);
        createEntityDTOMapper = builderTypeMapper(CreateHorseDTO.class, CreateHorseEntityDTO.Builder.class);
        updateDTOMapper = builderTypeMapper(UpdateHorseRequestDTO.class, UpdateHorseDTO.Builder.class);
        updateEntityDTOMapper = builderTypeMapper(UpdateHorseDTO.class, UpdateHorseEntityDTO.Builder.class);
        findDTOMapper = builderTypeMapper(FindHorsesQuery.class, FindHorsesDTO.Builder.class);
    }

    @Override
    public CreateHorseDTO toCreateDTO(CreateHorseRequestDTO dto) {
        return createDTOMapper
                .map(dto)
                .build();
    }

    @Override
    public CreateHorseEntityDTO toCreateEntityDTO(CreateHorseDTO dto) {
        return createEntityDTOMapper
                .map(dto)
                .uuid(UUID.randomUUID())
                .state(HorseModelState.ACTIVE)
                .build();
    }

    @Override
    public UpdateHorseDTO toUpdateDTO(UUID horseUuid, UpdateHorseRequestDTO dto) {
        return updateDTOMapper
                .map(dto)
                .uuid(horseUuid)
                .build();
    }

    @Override
    public UpdateHorseEntityDTO toUpdateEntityDTO(UpdateHorseDTO dto) {
        return updateEntityDTOMapper
                .map(dto)
                .build();
    }

    @Override
    public FindHorsesDTO toFindDTO(FindHorsesQuery findHorsesQuery) {
        return findDTOMapper
                .map(findHorsesQuery)
                .build();
    }

    @Override
    public HorseModelState toModel(HorseStateDTO state) {
        return HorseModelState.valueOf(state.name());
    }

    @Override
    public List<HorseModelState> toModel(Collection<HorseStateDTO> states) {
        return states.stream().map(this::toModel).toList();
    }
}
