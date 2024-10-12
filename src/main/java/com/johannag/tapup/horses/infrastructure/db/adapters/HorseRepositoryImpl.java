package com.johannag.tapup.horses.infrastructure.db.adapters;

import com.johannag.tapup.auth.infrastructure.utils.SecurityContextUtils;
import com.johannag.tapup.globals.domain.mappers.GlobalDomainMapper;
import com.johannag.tapup.globals.infrastructure.utils.Logger;
import com.johannag.tapup.horseRaces.infrastructure.db.entities.HorseRaceEntityState;
import com.johannag.tapup.horses.application.dtos.FindHorsesDTO;
import com.johannag.tapup.horses.domain.dtos.CreateHorseEntityDTO;
import com.johannag.tapup.horses.domain.dtos.UpdateHorseEntityDTO;
import com.johannag.tapup.horses.domain.mappers.HorseDomainMapper;
import com.johannag.tapup.horses.domain.models.HorseModel;
import com.johannag.tapup.horses.infrastructure.db.entities.HorseEntity;
import com.johannag.tapup.horses.infrastructure.db.entities.HorseEntityState;
import com.johannag.tapup.horses.infrastructure.db.repositories.JpaHorseRepository;
import com.johannag.tapup.horses.infrastructure.db.repositories.JpaHorseSpecifications;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class HorseRepositoryImpl implements HorseRepository {

    private static final Logger logger = Logger.getLogger(HorseRepositoryImpl.class);
    private final JpaHorseRepository jpaHorseRepository;
    private final HorseDomainMapper horseDomainMapper;
    private final GlobalDomainMapper globalDomainMapper;

    @Override
    public boolean existsHorseByCode(String code) {
        return jpaHorseRepository.existsByCodeAndStateIn(code, HorseEntityState.existenceStates());
    }

    @Override
    @Transactional
    public HorseModel upsert(CreateHorseEntityDTO dto) {
        logger.info("Upserting horse in DB: {}", dto.toString());

        Optional<HorseEntity> maybeHorseEntity = jpaHorseRepository.findMaybeOneByCodeForUpdate(dto.getCode());

        if (maybeHorseEntity.isPresent()) {
            HorseEntity savedHorseEntity = maybeHorseEntity.get();
            savedHorseEntity.setName(dto.getName());
            savedHorseEntity.setBreed(dto.getBreed());
            savedHorseEntity.setBirthDate(dto.getBirthDate());
            savedHorseEntity.setSex(globalDomainMapper.toEntity(dto.getSex()));
            savedHorseEntity.setState(horseDomainMapper.toEntity(dto.getState()));
            savedHorseEntity.setColor(dto.getColor());
            savedHorseEntity.setUpdatedBy(SecurityContextUtils.userOnContextId());
            jpaHorseRepository.saveAndFlush(savedHorseEntity);

            return horseDomainMapper.toModelWithoutParticipations(savedHorseEntity);
        } else {
            HorseEntity newHorseEntity = horseDomainMapper.toEntity(dto);
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
        logger.info("Updating horse [{}] in DB", dto.getUuid());
        HorseEntity horse = jpaHorseRepository.findOneByUuidForUpdate(dto.getUuid());
        Optional.ofNullable(dto.getName()).ifPresent(horse::setName);
        Optional.ofNullable(dto.getBreed()).ifPresent(horse::setBreed);
        Optional.ofNullable(dto.getBirthDate()).ifPresent(horse::setBirthDate);
        Optional.ofNullable(dto.getSex()).ifPresent(sex -> horse.setSex(globalDomainMapper.toEntity(sex)));
        Optional.ofNullable(dto.getColor()).ifPresent(horse::setColor);
        Optional.ofNullable(dto.getState()).ifPresent(state -> horse.setState(horseDomainMapper.toEntity(state)));

        jpaHorseRepository.saveAndFlush(horse);

        return horseDomainMapper.toModelWithoutParticipations(horse);
    }

    @Override
    @Transactional
    public HorseModel deactivateByUuid(UUID uuid) {
        logger.info("Deactivating horse [{}] in DB", uuid);
        HorseEntity horse = jpaHorseRepository.findOneByUuidForUpdate(uuid);
        horse.setState(HorseEntityState.INACTIVE);

        jpaHorseRepository.saveAndFlush(horse);

        return horseDomainMapper.toModelWithoutParticipations(horse);
    }

    @Override
    public Page<HorseModel> findAll(FindHorsesDTO dto) {
        Pageable pageable = PageRequest.of(dto.getPage(), dto.getSize());

        Specification<HorseEntity> spec = new JpaHorseSpecifications.Builder()
                .withStates(horseDomainMapper.toEntity(dto.getStates()))
                .build();

        return jpaHorseRepository
                .findAll(spec, pageable)
                .map(horseDomainMapper::toModelWithoutParticipations);
    }

}
