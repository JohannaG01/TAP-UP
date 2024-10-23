package com.johannag.tapup.users.infrastructure.db.adapter;

import com.johannag.tapup.auth.infrastructure.utils.SecurityContextUtils;
import com.johannag.tapup.globals.infrastructure.utils.Logger;
import com.johannag.tapup.users.application.configs.UserSystemConfig;
import com.johannag.tapup.users.domain.dtos.AddUserFundsToEntityDTO;
import com.johannag.tapup.users.domain.dtos.CreateUserEntityDTO;
import com.johannag.tapup.users.domain.dtos.SubtractUserFundsToEntityDTO;
import com.johannag.tapup.users.domain.mappers.UserDomainMapper;
import com.johannag.tapup.users.domain.models.UserModel;
import com.johannag.tapup.users.infrastructure.db.entities.UserEntity;
import com.johannag.tapup.users.infrastructure.db.repositories.JpaUserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private static final Logger logger = Logger.getLogger(UserRepositoryImpl.class);
    private final JpaUserRepository jpaUserRepository;
    private final UserDomainMapper userDomainMapper;
    private final UserSystemConfig userSystemConfig;

    @Override
    public boolean existsUser(String email) {
        return jpaUserRepository.existsByEmail(email);
    }

    @Override
    public List<UserModel> findAll(Collection<UUID> userUuids) {
        List<UserEntity> users = jpaUserRepository.findAllByUuidIn(userUuids);

        return users.stream()
                .map(userDomainMapper::toModel)
                .toList();
    }

    @Override
    @Transactional
    public UserModel create(CreateUserEntityDTO dto) {
        logger.info("Saving user: {}", dto.toString());

        UserEntity userEntity = userDomainMapper.toEntity(dto);
        userEntity.setCreatedBy(userSystemConfig.getAdminUserId());
        userEntity.setUpdatedBy(userSystemConfig.getAdminUserId());

        jpaUserRepository.saveAndFlush(userEntity);
        userEntity.setCreatedBy(userEntity.getId());
        userEntity.setUpdatedBy(userEntity.getId());

        jpaUserRepository.save(userEntity);

        return userDomainMapper.toModel(userEntity);
    }

    @Override
    public Optional<UserModel> findMaybeByEmail(String email) {
        return jpaUserRepository.findMaybeOneByEmail(email)
                .map(userDomainMapper::toModel);
    }

    @Override
    public Optional<UserModel> findMaybeByUUID(UUID uuid) {
        return jpaUserRepository.findMaybeOneByUuid(uuid)
                .map(userDomainMapper::toModel);
    }

    @Override
    @Transactional
    public UserModel addFunds(AddUserFundsToEntityDTO dto) {
        logger.info("Adding funds to user in DB: {}", dto.getUserUuid());

        UserEntity userEntity = jpaUserRepository.findOneByUuidForUpdate(dto.getUserUuid());
        userEntity.addBalance(dto.getAmount());
        jpaUserRepository.saveAndFlush(jpaUserRepository.save(userEntity));

        return userDomainMapper.toModel(userEntity);
    }

    @Override
    @Transactional
    public List<UserModel> addFunds(List<AddUserFundsToEntityDTO> dtos) {
        logger.info("Adding funds to balance for {} users in DB", dtos.size());

        Map<UUID, BigDecimal> amountByUserUuid = dtos.stream()
                .collect(Collectors.toMap(AddUserFundsToEntityDTO::getUserUuid, AddUserFundsToEntityDTO::getAmount));

        List<UserEntity> users = jpaUserRepository.findByUuidForUpdate(amountByUserUuid.keySet());
        users.forEach(user -> {
            user.addBalance(amountByUserUuid.get(user.getUuid()));
        });

        jpaUserRepository.saveAllAndFlush(users);

        return users.stream()
                .map(userDomainMapper::toModel)
                .toList();
    }


    @Override
    public UserModel subtractFunds(SubtractUserFundsToEntityDTO dto) {
        logger.info("Subtracting funds from user: {}", dto.getUserUuid());

        UserEntity userEntity = jpaUserRepository.findOneByUuidForUpdate(dto.getUserUuid());
        userEntity.subtractBalance(dto.getAmount());
        jpaUserRepository.saveAndFlush(jpaUserRepository.save(userEntity));

        return userDomainMapper.toModel(userEntity);
    }

    @Override
    public Long findUserIdByEmail(String email) {
        return jpaUserRepository.findIdByEmail(email);
    }

    @Override
    public List<UserModel> findAllAdmins() {
        return jpaUserRepository.findByIsAdmin(true).stream()
                .map(userDomainMapper::toModel)
                .toList();
    }
}
