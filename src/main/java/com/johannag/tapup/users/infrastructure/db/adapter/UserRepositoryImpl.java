package com.johannag.tapup.users.infrastructure.db.adapter;

import com.johannag.tapup.configurations.UserSystemConfig;
import com.johannag.tapup.users.domain.dtos.CreateUserEntityDTO;
import com.johannag.tapup.users.domain.mappers.UserDomainMapper;
import com.johannag.tapup.users.domain.models.UserModel;
import com.johannag.tapup.users.infrastructure.db.entities.UserEntity;
import com.johannag.tapup.users.infrastructure.db.repositories.JpaUserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private static final Logger logger = LogManager.getLogger(UserRepositoryImpl.class);
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
        UserModel userModel = userDomainMapper.toModel(userEntity);

        logger.info("Saved user: {}", userModel.toString());

        return userModel;
    }

    @Override
    public Optional<UserModel> findByEmail(String email) {
        return jpaUserRepository.findByEmail(email)
                .map(userDomainMapper::toModel);
    }
}
