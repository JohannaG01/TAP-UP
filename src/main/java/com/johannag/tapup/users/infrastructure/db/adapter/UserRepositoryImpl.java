package com.johannag.tapup.users.infrastructure.db.adapter;

import com.johannag.tapup.globals.infrastructure.utils.Logger;
import com.johannag.tapup.users.application.configs.UserSystemConfig;
import com.johannag.tapup.users.domain.dtos.AddUserFundsToEntityDTO;
import com.johannag.tapup.users.domain.dtos.CreateUserEntityDTO;
import com.johannag.tapup.users.domain.mappers.UserDomainMapper;
import com.johannag.tapup.users.domain.models.UserModel;
import com.johannag.tapup.users.infrastructure.db.entities.UserEntity;
import com.johannag.tapup.users.infrastructure.db.repositories.JpaUserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private static final Logger logger = Logger.getLogger(UserRepositoryImpl.class);
    private final JpaUserRepository jpaUserRepository;
    private final UserDomainMapper userDomainMapper;
    private final UserSystemConfig userSystemConfig;

    @Override
    public boolean userExists(String email) {
        return jpaUserRepository.existsByEmail(email);
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
        logger.info("Adding funds to user: {}", dto.getUserUuid());
        UserEntity userEntity = jpaUserRepository.findOneByUuidForUpdate(dto.getUserUuid());
        userEntity.addBalance(dto.getAmount());
        jpaUserRepository.saveAndFlush(jpaUserRepository.save(userEntity));

        return userDomainMapper.toModel(userEntity);
    }

    @Override
    public Long findUserIdByEmail(String email) {
        return jpaUserRepository.findIdByEmail(email);
    }
}
