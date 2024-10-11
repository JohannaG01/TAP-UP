package com.johannag.tapup.horses.domain.mappers;

import com.johannag.tapup.globals.infrastructure.db.entities.SexEntity;
import com.johannag.tapup.horses.application.dtos.CreateHorseDTO;
import com.johannag.tapup.horses.domain.dtos.CreateHorseEntityDTO;
import com.johannag.tapup.horses.domain.models.HorseModel;
import com.johannag.tapup.horses.domain.models.HorseModelState;
import com.johannag.tapup.horses.infrastructure.db.entities.HorseEntity;
import com.johannag.tapup.horses.infrastructure.db.entities.HorseEntityState;
import com.johannag.tapup.horses.presentation.dtos.requests.CreateHorseRequestDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import static com.johannag.tapup.globals.application.utils.ModelMapperUtils.builderTypeMapper;

@Component
public class HorseDomainMapperImpl implements HorseDomainMapper {

    private final TypeMap<CreateHorseEntityDTO, HorseEntity.Builder> horseEntityMapper;
    private final TypeMap<HorseEntity, HorseModel.Builder> horseModelMapper;


    public HorseDomainMapperImpl() {
        horseEntityMapper = builderTypeMapper(CreateHorseEntityDTO.class, HorseEntity.Builder.class);
        horseModelMapper = builderTypeMapper(HorseEntity.class, HorseModel.Builder.class);
        horseModelMapper.addMappings(mapper -> mapper.skip(HorseModel.Builder::participations));
    }

    @Override
    public HorseEntity toHorseEntity(CreateHorseEntityDTO dto) {
        return horseEntityMapper
                .map(dto)
                .sex(SexEntity.valueOf(dto.getSex().name()))
                .state(HorseEntityState.valueOf(dto.getState().name()))
                .build();
    }

    @Override
    public HorseModel toModelWithoutParticipations(HorseEntity entity) {
        return horseModelMapper
                .map(entity)
                .build();
    }
}
