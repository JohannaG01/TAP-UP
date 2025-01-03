package com.johannag.tapup.bets.infrastructure.db.adapters;

import com.johannag.tapup.bets.domain.dtos.BetSummaryDTO;
import com.johannag.tapup.bets.domain.dtos.CreateBetEntityDTO;
import com.johannag.tapup.bets.domain.dtos.FindBetEntitiesDTO;
import com.johannag.tapup.bets.domain.dtos.UpdateBetEntitiesStateDTO;
import com.johannag.tapup.bets.domain.mappers.BetDomainMapper;
import com.johannag.tapup.bets.domain.models.BetModel;
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

import java.util.List;
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

        jpaBetRepository.saveAndFlush(bet);
        return betDomainMapper.toModel(bet);
    }

    @Override
    public Page<BetModel> findAll(FindBetEntitiesDTO dto) {
        Pageable pageable = PageRequest.of(dto.getPage(), dto.getSize(), Sort.by("createdAt").descending());

        Specification<BetEntity> spec = new JpaBetSpecifications.Builder()
                .withUserUuid(dto.getUserUuid())
                .withBetStates(betDomainMapper.toEntity(dto.getBetStates()))
                .withHorseRaceStates(horseRaceDomainMapper.toEntity(dto.getHorseRaceStates()))
                .withMinAmount(dto.getMinAmount())
                .withMaxAmount(dto.getMaxAmount())
                .withPlacement(dto.getPlacement())
                .withHorseUuid(dto.getHorseUuid())
                .withStartTimeFrom(dto.getStartTimeFrom())
                .withStartTimeTo(dto.getStartTimeTo())
                .build();

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

        return jpaBetRepository.findByParticipant_HorseRace_UuidAndState(horseRaceUuid, BetEntityState.PENDING,
                        pageable)
                .map(betDomainMapper::toModel);
    }

    @Override
    public Long countBetsByHorseRaceUuid(UUID horseRaceUuid) {
        return jpaBetRepository.countByParticipant_HorseRace_Uuid(horseRaceUuid);
    }

    @Override
    public Long countWinningBetsByHorseRaceUuid(UUID horseRaceUuid) {
        return jpaBetRepository.countByParticipant_HorseRace_UuidAndParticipant_Placement(horseRaceUuid, 1);
    }

    @Override
    @Transactional
    public List<BetModel> updateState(UpdateBetEntitiesStateDTO dto) {
        logger.info("Updating {} bets state {} in DB", dto.getBetUuid().size(), dto.getState());

        List<BetEntity> bets = jpaBetRepository.findAllByUuidForUpdate(dto.getBetUuid());
        jpaBetRepository.updateBetStates(dto.getBetUuid(), betDomainMapper.toEntity(dto.getState()));
        jpaBetRepository.flush();

        return bets.stream()
                .map(betDomainMapper::toModel)
                .toList();
    }
}
