package com.johannag.tapup.horses.infrastructure.db.adapters;

import com.johannag.tapup.auth.infrastructure.utils.SecurityContextUtils;
import com.johannag.tapup.globals.domain.mappers.GlobalDomainMapper;
import com.johannag.tapup.globals.infrastructure.utils.Logger;
import com.johannag.tapup.horses.domain.dtos.CreateHorseEntityDTO;
import com.johannag.tapup.horses.domain.dtos.FindHorseEntitiesDTO;
import com.johannag.tapup.horses.domain.dtos.UpdateHorseEntityDTO;
import com.johannag.tapup.horses.domain.mappers.HorseDomainMapper;
import com.johannag.tapup.horses.domain.models.HorseModel;
import com.johannag.tapup.horses.infrastructure.db.dtos.FindByUuidStatesAndDatesDTO;
import com.johannag.tapup.horses.infrastructure.db.dtos.FindByUuidsStateAndDatesQuery;
import com.johannag.tapup.horses.infrastructure.db.entities.HorseEntity;
import com.johannag.tapup.horses.infrastructure.db.entities.HorseEntityState;
import com.johannag.tapup.horses.infrastructure.db.repositories.JpaHorseRepository;
import com.johannag.tapup.horses.infrastructure.db.repositories.JpaHorseSpecifications;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.johannag.tapup.horseRaces.infrastructure.db.entities.HorseRaceEntityState.FINISHED;
import static com.johannag.tapup.horseRaces.infrastructure.db.entities.HorseRaceEntityState.SCHEDULED;

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

            return horseDomainMapper.toModel(savedHorseEntity);
        } else {
            HorseEntity newHorseEntity = horseDomainMapper.toEntity(dto);
            newHorseEntity.setCreatedBy(SecurityContextUtils.userOnContextId());
            newHorseEntity.setUpdatedBy(SecurityContextUtils.userOnContextId());
            jpaHorseRepository.saveAndFlush(newHorseEntity);

            return horseDomainMapper.toModel(newHorseEntity);
        }

    }

    @Override
    public Optional<HorseModel> findOneMaybeByUuid(UUID uuid) {
        return jpaHorseRepository.findOneMaybeByUuidAndStateIn(uuid, HorseEntityState.existenceStates())
                .map(horseDomainMapper::toModel);

    }

    @Override
    public boolean isHorseInScheduledMatch(UUID uuid) {
        return jpaHorseRepository.existsByUuidAndParticipations_HorseRace_State(uuid, SCHEDULED);
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

        return horseDomainMapper.toModel(horse);
    }

    @Override
    @Transactional
    public HorseModel deactivateByUuid(UUID uuid) {
        logger.info("Deactivating horse [{}] in DB", uuid);
        HorseEntity horse = jpaHorseRepository.findOneByUuidForUpdate(uuid);
        horse.setState(HorseEntityState.INACTIVE);

        jpaHorseRepository.saveAndFlush(horse);

        return horseDomainMapper.toModel(horse);
    }

    @Override
    public Page<HorseModel> findAll(FindHorseEntitiesDTO dto) {
        Pageable pageable = PageRequest.of(dto.getPage(), dto.getSize());

        Specification<HorseEntity> spec = new JpaHorseSpecifications.Builder()
                .withStates(horseDomainMapper.toEntity(dto.getStates()))
                .withName(dto.getName())
                .withCode(dto.getCode())
                .withSex(globalDomainMapper.toEntity(dto.getSex()))
                .withBirthDateFrom(dto.getBirthDateFrom())
                .withBirthDateTo(dto.getBirthDateTo())
                .withBreed(dto.getBreed())
                .withColor(dto.getColor())
                .build(Sort.by("createdAt").descending());

        return jpaHorseRepository
                .findAll(spec, pageable)
                .map(horseDomainMapper::toModel);
    }

    @Override
    public List<HorseModel> findActiveByUuidIn(List<UUID> uuid) {
        return jpaHorseRepository.findByUuidInAndState(uuid, HorseEntityState.ACTIVE).stream()
                .map(horseDomainMapper::toModel)
                .toList();
    }

    @Override
    public List<HorseModel> findByUuidsInScheduledRaceBeforeOrInFinishedRaceAfter(FindByUuidStatesAndDatesDTO dto) {

        FindByUuidsStateAndDatesQuery query = FindByUuidsStateAndDatesQuery.builder()
                .uuids(dto.getUuids())
                .pastStates(List.of(SCHEDULED, FINISHED))
                .startTimeFrom(dto.getPastDateTime())
                .futureState(SCHEDULED)
                .startTimeTo(dto.getFutureDateTime())
                .raceDateTime(dto.getRaceDateTime())
                .horseRaceUuidsToExclude(dto.getHorseRaceUuidsToExclude())
                .build();

        return jpaHorseRepository
                .findByUuidsWithRaceInStatesBetweenDates(query)
                .stream()
                .map(horseDomainMapper::toModel)
                .toList();

    }


}
