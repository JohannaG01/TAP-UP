package com.johannag.tapup.bets.infrastructure.db.adapters;

import com.johannag.tapup.auth.infrastructure.utils.SecurityContextUtils;
import com.johannag.tapup.bets.domain.dtos.CreateBetEntityDTO;
import com.johannag.tapup.bets.domain.mappers.BetDomainMapper;
import com.johannag.tapup.bets.domain.models.BetModel;
import com.johannag.tapup.bets.infrastructure.db.entities.BetEntity;
import com.johannag.tapup.bets.infrastructure.db.repositories.JpaBetRepository;
import com.johannag.tapup.globals.infrastructure.utils.Logger;
import com.johannag.tapup.horseRaces.domain.mappers.HorseRaceDomainMapper;
import com.johannag.tapup.horseRaces.infrastructure.db.adapters.HorseRaceRepositoryImpl;
import com.johannag.tapup.horseRaces.infrastructure.db.entities.ParticipantEntity;
import com.johannag.tapup.horseRaces.infrastructure.db.repositories.JpaParticipantRepository;
import com.johannag.tapup.horses.infrastructure.db.repositories.JpaHorseRepository;
import com.johannag.tapup.users.infrastructure.db.entities.UserEntity;
import com.johannag.tapup.users.infrastructure.db.repositories.JpaUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@AllArgsConstructor
public class BetRepositoryImpl implements BetRepository {

    private static final Logger logger = Logger.getLogger(BetRepositoryImpl.class);
    private final BetDomainMapper betDomainMapper;
    private final JpaBetRepository jpaBetRepository;
    private final JpaUserRepository jpaUserRepository;
    private final JpaParticipantRepository jpaParticipantRepository;

    @Override
    public BetModel create(CreateBetEntityDTO dto) {
        logger.info("Saving in DB bet");
        UserEntity user = jpaUserRepository.findOneByUuid(dto.getUserUuid());
        ParticipantEntity participantEntity = jpaParticipantRepository.findOneByUuid(dto.getParticipantUuid());

        BetEntity bet = betDomainMapper.toEntity(dto, user, participantEntity);
        bet.setCreatedBy(SecurityContextUtils.userOnContextId());
        bet.setUpdatedBy(SecurityContextUtils.userOnContextId());

        jpaBetRepository.saveAndFlush(bet);
        return betDomainMapper.toModel(bet);
    }
}
