package com.johannag.tapup.bets.infrastructure.db.adapters;

import com.johannag.tapup.auth.infrastructure.utils.SecurityContextUtils;
import com.johannag.tapup.bets.domain.dtos.BetSummaryDTO;
import com.johannag.tapup.bets.domain.dtos.CreateBetEntityDTO;
import com.johannag.tapup.bets.domain.dtos.FindBetEntitiesDTO;
import com.johannag.tapup.bets.domain.dtos.UpdateBetEntityStateDTO;
import com.johannag.tapup.bets.domain.mappers.BetDomainMapper;
import com.johannag.tapup.bets.domain.models.BetModel;
import com.johannag.tapup.bets.domain.models.BetModelState;
import com.johannag.tapup.bets.infrastructure.db.entities.BetEntity;
import com.johannag.tapup.bets.infrastructure.db.entities.BetEntityState;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
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

    @Override
    public Page<BetModel> findPendingBetsByHorseRaceUuid(UUID horseRaceUuid, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("user.id"));

        return jpaBetRepository.findByParticipant_HorseRace_UuidAndState(horseRaceUuid, BetEntityState.PENDING, pageable)
                .map(betDomainMapper::toModel);
    }

    @Override
    @Transactional
    public List<BetModel> updateState(List<UpdateBetEntityStateDTO> dtos) {
        logger.info("Updating bet states in DB");
        Map<UUID, BetModelState> stateByBetUuid = new HashMap<>();

        for (UpdateBetEntityStateDTO dto : dtos) {
            stateByBetUuid.put(dto.getBetUuid(), dto.getState());
        }

        List<BetEntity> bets = jpaBetRepository.findAllByUuidForUpdate(stateByBetUuid.keySet());
        bets.forEach(bet -> {
            BetEntityState state = betDomainMapper.toEntity(stateByBetUuid.get(bet.getUuid()));
            bet.setState(state);
            bet.setUpdatedBy(SecurityContextUtils.userOnContextId());
        });

        jpaBetRepository.saveAllAndFlush(bets);

        return bets.stream()
                .map(betDomainMapper::toModel)
                .toList();
    }
}
