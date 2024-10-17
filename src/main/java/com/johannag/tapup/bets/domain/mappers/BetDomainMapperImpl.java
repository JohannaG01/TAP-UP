package com.johannag.tapup.bets.domain.mappers;

import com.johannag.tapup.bets.domain.dtos.CreateBetEntityDTO;
import com.johannag.tapup.bets.domain.models.BetModel;
import com.johannag.tapup.bets.domain.models.BetModelState;
import com.johannag.tapup.bets.infrastructure.db.entities.BetEntity;
import com.johannag.tapup.bets.infrastructure.db.entities.BetEntityState;
import com.johannag.tapup.horseRaces.domain.mappers.ParticipantDomainMapper;
import com.johannag.tapup.horseRaces.infrastructure.db.entities.ParticipantEntity;
import com.johannag.tapup.users.domain.mappers.UserDomainMapper;
import com.johannag.tapup.users.infrastructure.db.entities.UserEntity;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import static com.johannag.tapup.globals.application.utils.ModelMapperUtils.builderTypeMapper;

@Component
public class BetDomainMapperImpl implements BetDomainMapper {

    private final UserDomainMapper userDomainMapper;
    private final ParticipantDomainMapper participantDomainMapper;
    private final TypeMap<BetEntity, BetModel.Builder> modelMapper;

    public BetDomainMapperImpl(ParticipantDomainMapper participantDomainMapper, UserDomainMapper userDomainMapper) {
        this.participantDomainMapper = participantDomainMapper;
        this.userDomainMapper = userDomainMapper;
        modelMapper = builderTypeMapper(BetEntity.class, BetModel.Builder.class);
        modelMapper.addMappings(mapper -> mapper.skip(BetModel.Builder::participant));
        modelMapper.addMappings(mapper -> mapper.skip(BetModel.Builder::user));
    }

    @Override
    public BetModel toModel(BetEntity entity) {
        return modelMapper
                .map(entity)
                .user(userDomainMapper.toModelWithoutBets(entity.getUser()))
                .participant(participantDomainMapper.toModel(entity.getParticipant()))
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
}
