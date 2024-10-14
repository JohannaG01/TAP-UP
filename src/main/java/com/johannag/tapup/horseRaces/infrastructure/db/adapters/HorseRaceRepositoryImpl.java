package com.johannag.tapup.horseRaces.infrastructure.db.adapters;

import com.johannag.tapup.auth.infrastructure.utils.SecurityContextUtils;
import com.johannag.tapup.globals.infrastructure.utils.Logger;
import com.johannag.tapup.horseRaces.domain.UpdateHorseRaceEntityDTO;
import com.johannag.tapup.horseRaces.domain.dtos.CreateHorseRaceEntityDTO;
import com.johannag.tapup.horseRaces.domain.mappers.HorseRaceDomainMapper;
import com.johannag.tapup.horseRaces.domain.models.HorseRaceModel;
import com.johannag.tapup.horseRaces.infrastructure.db.entities.HorseRaceEntity;
import com.johannag.tapup.horseRaces.infrastructure.db.repositories.JpaHorseRaceRepository;
import com.johannag.tapup.horses.infrastructure.db.entities.HorseEntity;
import com.johannag.tapup.horses.infrastructure.db.repositories.JpaHorseRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class HorseRaceRepositoryImpl implements HorseRaceRepository {

    private static final Logger logger = Logger.getLogger(HorseRaceRepositoryImpl.class);
    private final HorseRaceDomainMapper horseRaceDomainMapper;
    private final JpaHorseRepository jpaHorseRepository;
    private final JpaHorseRaceRepository jpaHorseRaceRepository;

    @Override
    @Transactional
    public HorseRaceModel create(CreateHorseRaceEntityDTO dto) {
        logger.info("Saving in DB horse race");
        List<HorseEntity> horses = jpaHorseRepository.findByUuidIn(dto.getHorseUuids());

        HorseRaceEntity horseRace = horseRaceDomainMapper.toEntity(dto, horses);
        horseRace.setCreatedBy(SecurityContextUtils.userOnContextId());
        horseRace.setUpdatedBy(SecurityContextUtils.userOnContextId());

        horseRace.getParticipants()
                .forEach(participant -> {
                    participant.setCreatedBy(SecurityContextUtils.userOnContextId());
                    participant.setUpdatedBy(SecurityContextUtils.userOnContextId());
                });

        jpaHorseRaceRepository.saveAndFlush(horseRace);
        return horseRaceDomainMapper.toModelWithoutBidirectional(horseRace);
    }

    @Override
    public Optional<HorseRaceModel> findOneMaybeByUuid(UUID uuid) {
        return jpaHorseRaceRepository
                .findOneMaybeByUuid(uuid)
                .map(horseRaceDomainMapper::toModelWithoutBidirectional);
    }

    @Override
    @Transactional
    public HorseRaceModel update(UpdateHorseRaceEntityDTO dto) {
        logger.info("Updating in DB horse race");
        HorseRaceEntity horseRace = jpaHorseRaceRepository.findOneByUuidForUpdate(dto.getRaceUuid());
        horseRace.setStartTime(dto.getStartTime());
        horseRace.setUpdatedBy(SecurityContextUtils.userOnContextId());

        jpaHorseRaceRepository.save(horseRace);
        HorseRaceEntity updatedHorseRace = jpaHorseRaceRepository.findOneByUuid(dto.getRaceUuid());
        return horseRaceDomainMapper.toModelWithoutBidirectional(updatedHorseRace);
    }
}
