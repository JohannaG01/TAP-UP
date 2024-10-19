package com.johannag.tapup.bets.domain.mappers;

import com.johannag.tapup.bets.domain.dtos.BetSummaryDTO;
import com.johannag.tapup.bets.domain.dtos.CreateBetEntityDTO;
import com.johannag.tapup.bets.domain.models.BetModel;
import com.johannag.tapup.bets.domain.models.BetModelState;
import com.johannag.tapup.bets.infrastructure.db.entities.BetEntity;
import com.johannag.tapup.bets.infrastructure.db.entities.BetEntityState;
import com.johannag.tapup.bets.infrastructure.db.projections.BetSummaryProjection;
import com.johannag.tapup.horseRaces.domain.mappers.ParticipantDomainMapper;
import com.johannag.tapup.horseRaces.domain.models.ParticipantModel;
import com.johannag.tapup.horseRaces.infrastructure.db.entities.ParticipantEntity;
import com.johannag.tapup.horses.domain.mappers.HorseDomainMapper;
import com.johannag.tapup.horses.domain.models.HorseModel;
import com.johannag.tapup.users.domain.mappers.UserDomainMapper;
import com.johannag.tapup.users.domain.models.UserModel;
import com.johannag.tapup.users.infrastructure.db.entities.UserEntity;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.johannag.tapup.globals.application.utils.ModelMapperUtils.builderTypeMapper;

@Component
public class BetDomainMapperImpl implements BetDomainMapper {

    private final UserDomainMapper userDomainMapper;
    private final ParticipantDomainMapper participantDomainMapper;
    private final HorseDomainMapper horseDomainMapper;
    private final TypeMap<BetEntity, BetModel.Builder> entityModelMapper;
    private final TypeMap<BetSummaryProjection, BetSummaryDTO.Builder> projectionModelMapper;


    public BetDomainMapperImpl(ParticipantDomainMapper participantDomainMapper, UserDomainMapper userDomainMapper,
                               HorseDomainMapper horseDomainMapper) {
        this.participantDomainMapper = participantDomainMapper;
        this.userDomainMapper = userDomainMapper;
        this.horseDomainMapper = horseDomainMapper;

        entityModelMapper = builderTypeMapper(BetEntity.class, BetModel.Builder.class);
        entityModelMapper.addMappings(mapper -> mapper.skip(BetModel.Builder::participant));
        entityModelMapper.addMappings(mapper -> mapper.skip(BetModel.Builder::user));

        projectionModelMapper = builderTypeMapper(BetSummaryProjection.class, BetSummaryDTO.Builder.class);
        projectionModelMapper.addMappings(mapper -> mapper.skip(BetSummaryDTO.Builder::horse));
    }

    @Override
    public BetModel toModel(BetEntity entity) {
        UserModel user = userDomainMapper.toModel(entity.getUser());
        ParticipantModel participant = participantDomainMapper.toModel(entity.getParticipant());

        return entityModelMapper
                .map(entity)
                .user(user)
                .participant(participant)
                .build();
    }

    @Override
    public BetEntity toEntity(CreateBetEntityDTO dto, UserEntity user, ParticipantEntity participant) {
        return BetEntity.builder()
                .uuid(dto.getUuid())
                .state(toEntity(dto.getState()))
                .amount(dto.getAmount())
                .user(user)
                .participant(participant)
                .build();
    }

    @Override
    public BetEntityState toEntity(BetModelState state) {
        return BetEntityState.valueOf(state.name());
    }

    @Override
    public List<BetEntityState> toEntity(Collection<BetModelState> states) {
        return states.stream()
                .map(this::toEntity)
                .toList();
    }

    @Override
    public List<BetSummaryDTO> toModel(List<BetSummaryProjection> projections) {
        return projections.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    private BetSummaryDTO toModel(BetSummaryProjection projection) {
        HorseModel horse = horseDomainMapper.toModel(projection.getHorse());

        return projectionModelMapper
                .map(projection)
                .horse(horse)
                .build();
    }

}
