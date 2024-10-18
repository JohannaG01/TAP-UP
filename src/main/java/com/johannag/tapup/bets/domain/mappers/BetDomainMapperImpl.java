package com.johannag.tapup.bets.domain.mappers;

import com.johannag.tapup.bets.domain.dtos.CreateBetEntityDTO;
import com.johannag.tapup.bets.domain.models.BetModel;
import com.johannag.tapup.bets.domain.models.BetModelState;
import com.johannag.tapup.bets.domain.models.BetSummaryModel;
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

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.johannag.tapup.globals.application.utils.ModelMapperUtils.builderTypeMapper;

@Component
public class BetDomainMapperImpl implements BetDomainMapper {

    private final UserDomainMapper userDomainMapper;
    private final ParticipantDomainMapper participantDomainMapper;
    private final HorseDomainMapper horseDomainMapper;
    private final TypeMap<BetEntity, BetModel.Builder> entityModelMapper;
    private final TypeMap<BetSummaryProjection, BetSummaryModel.Builder> proyectionModelMapper;


    public BetDomainMapperImpl(ParticipantDomainMapper participantDomainMapper, UserDomainMapper userDomainMapper,
                               HorseDomainMapper horseDomainMapper) {
        this.participantDomainMapper = participantDomainMapper;
        this.userDomainMapper = userDomainMapper;
        this.horseDomainMapper = horseDomainMapper;

        entityModelMapper = builderTypeMapper(BetEntity.class, BetModel.Builder.class);
        entityModelMapper.addMappings(mapper -> mapper.skip(BetModel.Builder::participant));
        entityModelMapper.addMappings(mapper -> mapper.skip(BetModel.Builder::user));

        proyectionModelMapper = builderTypeMapper(BetSummaryProjection.class, BetSummaryModel.Builder.class);
        proyectionModelMapper.addMappings(mapper -> mapper.skip(BetSummaryModel.Builder::horse));
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
    public List<BetSummaryModel> toModel(List<BetSummaryProjection> projections, List<Object[]> payouts,
                                         List<Object[]> amountWagered) {
        Map<UUID, BigDecimal> payoutMap = payouts.stream()
                .collect(Collectors.toMap(payout -> (UUID) payout[0], payout -> (BigDecimal) payout[1]));

        Map<UUID, BigDecimal> amountWageredMap = amountWagered.stream()
                .collect(Collectors.toMap(amount -> (UUID) amount[0], amount -> (BigDecimal) amount[1]));

        return projections.stream()
                .map(projection -> toModel(projection, payoutMap, amountWageredMap))
                .collect(Collectors.toList());
    }

    private BetSummaryModel toModel(BetSummaryProjection projection, Map<UUID, BigDecimal> payoutMap,
                                    Map<UUID, BigDecimal> amountWageredMap) {
        HorseModel horse = horseDomainMapper.toModel(projection.getHorse());
        BigDecimal totalPayout = payoutMap.getOrDefault(projection.getHorse().getUuid(), BigDecimal.ZERO);
        BigDecimal totalWagered = amountWageredMap.getOrDefault(projection.getHorse().getUuid(), BigDecimal.ZERO);

        return proyectionModelMapper
                .map(projection)
                .horse(horse)
                .totalPayouts(totalPayout)
                .totalAmountWagered(totalWagered)
                .build();
    }

}
