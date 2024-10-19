package com.johannag.tapup.bets.infrastructure.db.adapters;

import com.johannag.tapup.auth.infrastructure.utils.SecurityContextUtils;
import com.johannag.tapup.bets.domain.dtos.CreateBetEntityDTO;
import com.johannag.tapup.bets.domain.dtos.FindBetEntitiesDTO;
import com.johannag.tapup.bets.domain.mappers.BetDomainMapper;
import com.johannag.tapup.bets.domain.models.BetModel;
import com.johannag.tapup.bets.domain.dtos.BetSummaryDTO;
import com.johannag.tapup.bets.infrastructure.db.entities.BetEntity;
import com.johannag.tapup.bets.infrastructure.db.projections.BetSummaryProjection;
import com.johannag.tapup.bets.infrastructure.db.repositories.JpaBetRepository;
import com.johannag.tapup.bets.infrastructure.db.repositories.JpaBetSpecifications;
import com.johannag.tapup.globals.infrastructure.utils.Logger;
import com.johannag.tapup.horseRaces.domain.mappers.HorseRaceDomainMapper;
import com.johannag.tapup.horseRaces.infrastructure.db.entities.ParticipantEntity;
import com.johannag.tapup.horseRaces.infrastructure.db.repositories.JpaParticipantRepository;
import com.johannag.tapup.users.infrastructure.db.entities.UserEntity;
import com.johannag.tapup.users.infrastructure.db.repositories.JpaUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class BetRepositoryImpl implements BetRepository {

    private static final Logger logger = Logger.getLogger(BetRepositoryImpl.class);
    private final BetDomainMapper betDomainMapper;
    private final HorseRaceDomainMapper horseRaceDomainMapper;
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

    @Override
    public Page<BetModel> findAll(FindBetEntitiesDTO dto) {
        Pageable pageable = PageRequest.of(dto.getPage(), dto.getSize());

        Specification<BetEntity> spec = new JpaBetSpecifications.Builder()
                .withBetStates(betDomainMapper.toEntity(dto.getBetStates()))
                .withHorseRaceStates(horseRaceDomainMapper.toEntity(dto.getHorseRaceStates()))
                .withMinAmount(dto.getMinAmount())
                .withMaxAmount(dto.getMaxAmount())
                .withPlacement(dto.getPlacement())
                .withHorseUuid(dto.getHorseUuid())
                .withStartTimeFrom(dto.getStartTimeFrom())
                .withStartTimeTo(dto.getStartTimeTo())
                .build(Sort.by("createdAt").descending());

        return jpaBetRepository
                .findAll(spec, pageable)
                .map(betDomainMapper::toModel);
    }

    @Override
    public List<BetSummaryDTO> findBetDetails(UUID horseRaceUuid) {
        List<BetSummaryProjection> projections = jpaBetRepository.findBetDetails(horseRaceUuid);
        return betDomainMapper.toModel(projections);
    }
}
