package com.johannag.tapup.horses.infrastructure.db.adapters;

import com.johannag.tapup.auth.infrastructure.utils.SecurityContextUtils;
import com.johannag.tapup.globals.infrastructure.db.entities.SexEntity;
import com.johannag.tapup.globals.infrastructure.utils.Logger;
import com.johannag.tapup.horseRaces.infrastructure.db.entities.HorseRaceEntityState;
import com.johannag.tapup.horses.domain.dtos.CreateHorseEntityDTO;
import com.johannag.tapup.horses.domain.dtos.UpdateHorseEntityDTO;
import com.johannag.tapup.horses.domain.mappers.HorseDomainMapper;
import com.johannag.tapup.horses.domain.models.HorseModel;
import com.johannag.tapup.horses.infrastructure.db.entities.HorseEntity;
import com.johannag.tapup.horses.infrastructure.db.entities.HorseEntityState;
import com.johannag.tapup.horses.infrastructure.db.repositories.JpaHorseRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class HorseRepositoryImpl implements HorseRepository {

    private static final Logger logger = Logger.getLogger(HorseRepositoryImpl.class);
    private final JpaHorseRepository jpaHorseRepository;
    private final HorseDomainMapper horseDomainMapper;

    @Override
    public boolean existsHorseByCode(String code) {
        return jpaHorseRepository.existsByCodeAndStateIn(code, HorseEntityState.existenceStates());
    }

    @Override
    @Transactional
    public HorseModel upsert(CreateHorseEntityDTO dto) {
        logger.info("Upserting horse: {}", dto.toString());

        Optional<HorseEntity> maybeHorseEntity = jpaHorseRepository.findMaybeOneByCodeForUpdate(dto.getCode());

        if (maybeHorseEntity.isPresent()) {
            HorseEntity savedHorseEntity = maybeHorseEntity.get();
            savedHorseEntity.setName(dto.getName());
            savedHorseEntity.setBreed(dto.getBreed());
            savedHorseEntity.setBirthDate(dto.getBirthDate());
            savedHorseEntity.setSex(SexEntity.valueOf(dto.getSex().name()));
            savedHorseEntity.setState(HorseEntityState.valueOf(dto.getState().name()));
            savedHorseEntity.setColor(dto.getColor());
            savedHorseEntity.setUpdatedBy(SecurityContextUtils.userOnContextId());
            jpaHorseRepository.saveAndFlush(savedHorseEntity);

            return horseDomainMapper.toModelWithoutParticipations(savedHorseEntity);
        } else {
            HorseEntity newHorseEntity = horseDomainMapper.toHorseEntity(dto);
            newHorseEntity.setCreatedBy(SecurityContextUtils.userOnContextId());
            newHorseEntity.setUpdatedBy(SecurityContextUtils.userOnContextId());
            jpaHorseRepository.saveAndFlush(newHorseEntity);

            return horseDomainMapper.toModelWithoutParticipations(newHorseEntity);
        }

    }

    @Override
    public Optional<HorseModel> findMaybeByUuid(UUID uuid) {
        return jpaHorseRepository.findMaybeOneByUuidAndStateIn(uuid, HorseEntityState.existenceStates())
                .map(horseDomainMapper::toModelWithoutParticipations);

    }

    @Override
    public boolean isHorseInScheduledMatch(UUID uuid) {
        return jpaHorseRepository.existsByUuidAndParticipations_HorseRace_State(uuid, HorseRaceEntityState.SCHEDULED);
    }

    @Override
    @Transactional
    public HorseModel update(UpdateHorseEntityDTO dto) {
        HorseEntity horse = jpaHorseRepository.findOneByUuidForUpdate(dto.getUuid());
        Optional.ofNullable(dto.getName()).ifPresent(horse::setName);
        Optional.ofNullable(dto.getBreed()).ifPresent(horse::setBreed);
        Optional.ofNullable(dto.getBirthDate()).ifPresent(horse::setBirthDate);
        Optional.ofNullable(dto.getSex()).ifPresent(sex -> SexEntity.valueOf(sex.name()));
        Optional.ofNullable(dto.getColor()).ifPresent(horse::setColor);
        Optional.ofNullable(dto.getState()).ifPresent(state -> HorseEntityState.valueOf(state.name()));

        jpaHorseRepository.saveAndFlush(horse);

        return horseDomainMapper.toModelWithoutParticipations(horse);
    }

    @Override
    @Transactional
    public HorseModel deactivateByUuid(UUID uuid) {
        HorseEntity horse = jpaHorseRepository.findOneByUuidForUpdate(uuid);
        horse.setState(HorseEntityState.INACTIVE);

        jpaHorseRepository.saveAndFlush(horse);

        return horseDomainMapper.toModelWithoutParticipations(horse);
    }

}
