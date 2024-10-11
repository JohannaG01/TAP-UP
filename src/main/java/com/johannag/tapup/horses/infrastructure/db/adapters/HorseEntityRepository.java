package com.johannag.tapup.horses.infrastructure.db.adapters;

import com.johannag.tapup.auth.infrastructure.utils.SecurityContextUtils;
import com.johannag.tapup.globals.infrastructure.db.entities.SexEntity;
import com.johannag.tapup.globals.infrastructure.utils.Logger;
import com.johannag.tapup.horses.domain.dtos.CreateHorseEntityDTO;
import com.johannag.tapup.horses.domain.mappers.HorseDomainMapper;
import com.johannag.tapup.horses.domain.models.HorseModel;
import com.johannag.tapup.horses.infrastructure.db.entities.HorseEntity;
import com.johannag.tapup.horses.infrastructure.db.entities.HorseEntityState;
import com.johannag.tapup.horses.infrastructure.db.repositories.JpaHorseRepository;
import com.johannag.tapup.users.infrastructure.db.adapter.UserRepositoryImpl;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class HorseEntityRepository implements HorseRepository {

    private final JpaHorseRepository jpaHorseRepository;
    private final HorseDomainMapper horseDomainMapper;
    private static final Logger logger = Logger.getLogger(HorseEntityRepository.class);

    @Override
    public boolean existsHorseByCode(String code) {
        return jpaHorseRepository.existsByCodeAndStateIn(code, HorseEntityState.existenceStates());
    }

    @Override
    @Transactional
    public HorseModel upsert(CreateHorseEntityDTO dto) {
        logger.info("Saving horse: {}", dto.toString());

        Optional<HorseEntity> maybeHorseEntity = jpaHorseRepository.findMaybeOneByCode(dto.getCode());

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

            return horseDomainMapper.toModel(savedHorseEntity);
        } else {
            HorseEntity newHorseEntity = horseDomainMapper.toHorseEntity(dto);
            newHorseEntity.setCreatedBy(SecurityContextUtils.userOnContextId());
            newHorseEntity.setUpdatedBy(SecurityContextUtils.userOnContextId());
            jpaHorseRepository.saveAndFlush(newHorseEntity);

            return horseDomainMapper.toModel(newHorseEntity);
        }

    }

}
