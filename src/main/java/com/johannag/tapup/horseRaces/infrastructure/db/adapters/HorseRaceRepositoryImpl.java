package com.johannag.tapup.horseRaces.infrastructure.db.adapters;

import com.johannag.tapup.globals.infrastructure.utils.Logger;
import com.johannag.tapup.horseRaces.domain.UpdateHorseRaceEntityDTO;
import com.johannag.tapup.horseRaces.domain.dtos.CreateHorseRaceEntityDTO;
import com.johannag.tapup.horseRaces.domain.dtos.FindHorseRaceEntitiesDTO;
import com.johannag.tapup.horseRaces.domain.dtos.SubmitHorseRaceResultsForEntityDTO;
import com.johannag.tapup.horseRaces.domain.mappers.HorseRaceDomainMapper;
import com.johannag.tapup.horseRaces.domain.models.HorseRaceModel;
import com.johannag.tapup.horseRaces.infrastructure.db.entities.HorseRaceEntity;
import com.johannag.tapup.horseRaces.infrastructure.db.entities.HorseRaceEntityState;
import com.johannag.tapup.horseRaces.infrastructure.db.entities.ParticipantEntity;
import com.johannag.tapup.horseRaces.infrastructure.db.repositories.JpaHorseRaceRepository;
import com.johannag.tapup.horseRaces.infrastructure.db.repositories.JpaHorseRaceSpecifications;
import com.johannag.tapup.horseRaces.infrastructure.db.repositories.JpaParticipantRepository;
import com.johannag.tapup.horses.infrastructure.db.entities.HorseEntity;
import com.johannag.tapup.horses.infrastructure.db.repositories.JpaHorseRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@AllArgsConstructor
public class HorseRaceRepositoryImpl implements HorseRaceRepository {

    private static final Logger logger = Logger.getLogger(HorseRaceRepositoryImpl.class);
    private final HorseRaceDomainMapper horseRaceDomainMapper;
    private final JpaHorseRepository jpaHorseRepository;
    private final JpaHorseRaceRepository jpaHorseRaceRepository;
    private final JpaParticipantRepository jpaParticipantRepository;

    @Override
    @Transactional
    public HorseRaceModel create(CreateHorseRaceEntityDTO dto) {
        logger.info("Saving in DB horse race");

        List<HorseEntity> horses = jpaHorseRepository.findByUuidIn(dto.getHorseUuids());
        HorseRaceEntity horseRace = horseRaceDomainMapper.toEntity(dto, horses);

        jpaHorseRaceRepository.saveAndFlush(horseRace);
        return horseRaceDomainMapper.toModel(horseRace);
    }

    @Override
    public Optional<HorseRaceModel> findOneMaybeByUuid(UUID uuid) {
        return jpaHorseRaceRepository
                .findOneMaybeByUuid(uuid)
                .map(horseRaceDomainMapper::toModel);
    }

    @Override
    @Transactional
    public HorseRaceModel update(UpdateHorseRaceEntityDTO dto) {
        logger.info("Updating in DB horse race");

        HorseRaceEntity horseRace = jpaHorseRaceRepository.findOneByUuidForUpdate(dto.getRaceUuid());
        horseRace.setStartTime(dto.getStartTime());
        jpaHorseRaceRepository.save(horseRace);

        HorseRaceEntity updatedHorseRace = jpaHorseRaceRepository.findOneByUuid(dto.getRaceUuid());
        return horseRaceDomainMapper.toModel(updatedHorseRace);
    }

    @Override
    public Page<HorseRaceModel> findAll(FindHorseRaceEntitiesDTO dto) {
        Pageable pageable = PageRequest.of(dto.getPage(), dto.getSize(), Sort.by("startTime").descending());

        Specification<HorseRaceEntity> spec = new JpaHorseRaceSpecifications.Builder()
                .withStates(horseRaceDomainMapper.toEntity(dto.getStates()))
                .withStartTimeFrom(dto.getStartTimeFrom())
                .withStartTimeTo(dto.getStartTimeTo())
                .withHorseUuid(dto.getHorseUuid())
                .withHorseCode(dto.getHorseCode())
                .withHorseName(dto.getHorseName())
                .withHorseBreed(dto.getHorseBreed())
                .build();

        Page<HorseRaceEntity> horseRaces = jpaHorseRaceRepository.findAll(spec, pageable);

        Map<UUID, List<ParticipantEntity>> participantsByRaceUuid = obtainParticipantsGroupedForRaces(horseRaces);
        horseRaces.forEach(horseRace -> horseRace.setParticipants(participantsByRaceUuid.get(horseRace.getUuid())));

        return horseRaces.map(horseRaceDomainMapper::toModel);
    }

    @Override
    public Optional<HorseRaceModel> findOneMaybeByParticipantUuid(UUID participantUuid) {
        return jpaHorseRaceRepository
                .findOneMaybeByParticipants_Uuid(participantUuid)
                .map(horseRaceDomainMapper::toModel);
    }

    @Override
    @Transactional
    public HorseRaceModel submitResults(SubmitHorseRaceResultsForEntityDTO dto) {
        logger.info("Updating Horse Race states and participants positions and time in DB");

        HorseRaceEntity horseRace = jpaHorseRaceRepository.findOneFetchedByUuidForUpdate(dto.getHorseRaceUuid());
        horseRace.setState(HorseRaceEntityState.FINISHED);
        horseRace.setEndTime(dto.getEndTime());

        horseRace.getParticipants()
                .forEach(participant -> {
                    participant.setPlacement(dto.getPlacementForParticipantUuid(participant.getUuid()));
                    participant.setTime(dto.getTimeForParticipantUuid(participant.getUuid()));
                });

        jpaHorseRaceRepository.saveAndFlush(horseRace);
        return horseRaceDomainMapper.toModel(horseRace);
    }

    @Override
    @Transactional
    public HorseRaceModel cancel(UUID horseRaceUuid) {
        logger.info("Updating state to CANCELLED for horse race {} in DB", horseRaceUuid);

        HorseRaceEntity horseRace = jpaHorseRaceRepository.findOneByUuidForUpdate(horseRaceUuid);
        horseRace.setState(HorseRaceEntityState.CANCELLED);
        jpaHorseRaceRepository.save(horseRace);

        HorseRaceEntity updatedHorseRace = jpaHorseRaceRepository.findOneByUuid(horseRaceUuid);
        return horseRaceDomainMapper.toModel(updatedHorseRace);
    }

    private Map<UUID, List<ParticipantEntity>> obtainParticipantsGroupedForRaces(Page<HorseRaceEntity> horseRaces) {
        List<UUID> horseRaceUuids = horseRaces.stream()
                .map(HorseRaceEntity::getUuid)
                .toList();

        List<Object[]> allParticipants = jpaParticipantRepository.findParticipantsGroupedByHorseRace(horseRaceUuids);

        return groupParticipantByRaceUuid(allParticipants);
    }

    private Map<UUID, List<ParticipantEntity>> groupParticipantByRaceUuid(List<Object[]> participants) {
        Map<UUID, List<ParticipantEntity>> groupedParticipants = new HashMap<>();

        for (Object[] result : participants) {
            UUID raceUuid = (UUID) result[0];
            ParticipantEntity participant = (ParticipantEntity) result[1];

            groupedParticipants.computeIfAbsent(raceUuid, key -> new ArrayList<>()).add(participant);
        }

        return groupedParticipants;
    }
}
