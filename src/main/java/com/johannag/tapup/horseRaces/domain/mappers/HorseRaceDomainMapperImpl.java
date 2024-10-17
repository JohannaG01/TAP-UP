package com.johannag.tapup.horseRaces.domain.mappers;

import com.johannag.tapup.horseRaces.domain.dtos.CreateHorseRaceEntityDTO;
import com.johannag.tapup.horseRaces.domain.dtos.CreateParticipantEntityDTO;
import com.johannag.tapup.horseRaces.domain.models.HorseRaceModel;
import com.johannag.tapup.horseRaces.domain.models.HorseRaceModelState;
import com.johannag.tapup.horseRaces.domain.models.ParticipantModel;
import com.johannag.tapup.horseRaces.infrastructure.db.entities.HorseRaceEntity;
import com.johannag.tapup.horseRaces.infrastructure.db.entities.HorseRaceEntityState;
import com.johannag.tapup.horseRaces.infrastructure.db.entities.ParticipantEntity;
import com.johannag.tapup.horses.infrastructure.db.entities.HorseEntity;
import org.hibernate.Hibernate;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.johannag.tapup.globals.application.utils.ModelMapperUtils.builderTypeMapper;

@Component
public class HorseRaceDomainMapperImpl implements HorseRaceDomainMapper {

    private final ParticipantDomainMapper participantDomainMapper;
    private final TypeMap<HorseRaceEntity, HorseRaceModel.Builder> modelMapper;

    public HorseRaceDomainMapperImpl(ParticipantDomainMapper participantDomainMapper) {
        this.participantDomainMapper = participantDomainMapper;
        modelMapper = builderTypeMapper(HorseRaceEntity.class, HorseRaceModel.Builder.class);
        modelMapper.addMappings(mapper -> mapper.skip(HorseRaceModel.Builder::participants));

    }

    @Override
    public HorseRaceEntity toEntity(CreateHorseRaceEntityDTO dto, List<HorseEntity> horses) {
        Map<UUID, HorseEntity> horseMapByUuid = createHorseMapByUuid(horses);
        List<ParticipantEntity> participants = createParticipants(dto.getParticipants(), horseMapByUuid);

        return buildHorseRace(dto, participants);
    }

    @Override
    public HorseRaceEntityState toEntity(HorseRaceModelState model) {
        return HorseRaceEntityState.valueOf(model.name());
    }

    @Override
    public List<HorseRaceEntityState> toEntity(Collection<HorseRaceModelState> models) {
        return models.stream()
                .map(this::toEntity)
                .toList();
    }

    @Override
    public HorseRaceModel toModel(HorseRaceEntity entity) {
        List<ParticipantModel> participants = entity.getParticipants().stream()
                .map(participantDomainMapper::toModelWithoutRace)
                .toList();

        return modelMapper
                .map(entity)
                .participants(participants)
                .build();
    }

    @Override
    public HorseRaceModel toModelWithoutHorseAndParticipants(HorseRaceEntity entity) {
        return modelMapper
                .map(entity)
                .build();
    }

    private Map<UUID, HorseEntity> createHorseMapByUuid(List<HorseEntity> horses) {
        return horses.stream()
                .collect(Collectors.toMap(HorseEntity::getUuid, horse -> horse));
    }

    private List<ParticipantEntity> createParticipants(List<CreateParticipantEntityDTO> participantDtos,
                                                       Map<UUID, HorseEntity> horseMapByUuid) {
        return participantDtos.stream()
                .map(dto -> toEntity(dto, horseMapByUuid.get(dto.getHorseUuid())))
                .collect(Collectors.toList());
    }

    private ParticipantEntity toEntity(CreateParticipantEntityDTO dto, HorseEntity horse) {
        return ParticipantEntity.builder()
                .uuid(dto.getUuid())
                .horse(horse)
                .build();
    }

    private HorseRaceEntity buildHorseRace(CreateHorseRaceEntityDTO dto, List<ParticipantEntity> participants) {
        HorseRaceEntity horseRace = HorseRaceEntity.builder()
                .uuid(dto.getUuid())
                .participants(participants)
                .startTime(dto.getStartTime())
                .state(toEntity(dto.getState()))
                .build();

        participants.forEach(participant -> participant.setHorseRace(horseRace));
        return horseRace;
    }

}
