package com.johannag.tapup.horseRaces.infrastructure.db.adapters;

import com.johannag.tapup.auth.infrastructure.utils.SecurityContextUtils;
import com.johannag.tapup.horseRaces.domain.dtos.CreateHorseRaceEntityDTO;
import com.johannag.tapup.horseRaces.domain.mappers.HorseRaceDomainMapper;
import com.johannag.tapup.horseRaces.domain.models.HorseRaceModel;
import com.johannag.tapup.horseRaces.infrastructure.db.entities.HorseRaceEntity;
import com.johannag.tapup.horseRaces.infrastructure.db.repositories.JpaHorseRaceEntityRepository;
import com.johannag.tapup.horses.infrastructure.db.entities.HorseEntity;
import com.johannag.tapup.horses.infrastructure.db.repositories.JpaHorseRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class HorseRaceRepositoryImpl implements HorseRaceRepository {

    private final HorseRaceDomainMapper horseRaceDomainMapper;
    private final JpaHorseRepository jpaHorseRepository;
    private final JpaHorseRaceEntityRepository jpaHorseRaceEntityRepository;

    @Override
    @Transactional
    public HorseRaceModel create(CreateHorseRaceEntityDTO dto) {
        List<HorseEntity> horses = jpaHorseRepository.findByUuidIn(dto.getHorseUuids());

        HorseRaceEntity horseRace = horseRaceDomainMapper.toEntity(dto, horses);
        horseRace.setCreatedBy(SecurityContextUtils.userOnContextId());
        horseRace.setUpdatedBy(SecurityContextUtils.userOnContextId());

        horseRace.getParticipants()
                .forEach(participant -> {
                    participant.setCreatedBy(SecurityContextUtils.userOnContextId());
                    participant.setUpdatedBy(SecurityContextUtils.userOnContextId());
                });

        jpaHorseRaceEntityRepository.saveAndFlush(horseRace);
        return horseRaceDomainMapper.toModelWithoutBidirectional(horseRace);
    }
}
