package com.johannag.tapup.horseRaces.domain.mappers;

import com.johannag.tapup.horseRaces.domain.models.ParticipantModel;
import com.johannag.tapup.horseRaces.infrastructure.db.entities.ParticipantEntity;
import com.johannag.tapup.horses.domain.mappers.HorseDomainMapper;
import com.johannag.tapup.horses.domain.models.HorseModel;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import static com.johannag.tapup.globals.application.utils.ModelMapperUtils.builderTypeMapper;

@Component
public class ParticipantDomainMapperImpl implements ParticipantDomainMapper {

    private final HorseDomainMapper horseDomainMapper;
    private final TypeMap<ParticipantEntity, ParticipantModel.Builder> modelMapper;

    public ParticipantDomainMapperImpl(HorseDomainMapper horseDomainMapper) {
        this.horseDomainMapper = horseDomainMapper;
        modelMapper = builderTypeMapper(ParticipantEntity.class, ParticipantModel.Builder.class);
        modelMapper.addMappings(mapper -> mapper.skip(ParticipantModel.Builder::horseRace));
        modelMapper.addMappings(mapper -> mapper.skip(ParticipantModel.Builder::horse));
    }

    @Override
    public ParticipantModel toModelWithoutRace(ParticipantEntity entity) {
        HorseModel horse = horseDomainMapper.toModelWithoutParticipations(entity.getHorse());
        return modelMapper
                .map(entity)
                .horse(horse)
                .build();
    }
}
