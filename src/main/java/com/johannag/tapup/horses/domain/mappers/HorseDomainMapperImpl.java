package com.johannag.tapup.horses.domain.mappers;

import com.johannag.tapup.horses.domain.dtos.CreateHorseEntityDTO;
import com.johannag.tapup.horses.domain.models.HorseModel;
import com.johannag.tapup.horses.domain.models.HorseModelState;
import com.johannag.tapup.horses.infrastructure.db.entities.HorseEntity;
import com.johannag.tapup.horses.infrastructure.db.entities.HorseEntityState;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

import static com.johannag.tapup.globals.application.utils.ModelMapperUtils.builderTypeMapper;

@Component
public class HorseDomainMapperImpl implements HorseDomainMapper {

    private final TypeMap<CreateHorseEntityDTO, HorseEntity.Builder> entityMapper;
    private final TypeMap<HorseEntity, HorseModel.Builder> modelMapper;


    public HorseDomainMapperImpl() {
        entityMapper = builderTypeMapper(CreateHorseEntityDTO.class, HorseEntity.Builder.class);
        modelMapper = builderTypeMapper(HorseEntity.class, HorseModel.Builder.class);
        modelMapper.addMappings(mapper -> mapper.skip(HorseModel.Builder::participations));
    }

    @Override
    public HorseEntity toEntity(CreateHorseEntityDTO dto) {
        return entityMapper
                .map(dto)
                .build();
    }

    @Override
    public HorseModel toModelWithoutParticipations(HorseEntity entity) {
        return modelMapper
                .map(entity)
                .build();
    }

    @Override
    public HorseEntityState toEntity(HorseModelState state) {
        return HorseEntityState.valueOf(state.name());
    }

    @Override
    public List<HorseEntityState> toEntity(Collection<HorseModelState> states) {
        return states.stream()
                .map(this::toEntity)
                .toList();
    }
}
