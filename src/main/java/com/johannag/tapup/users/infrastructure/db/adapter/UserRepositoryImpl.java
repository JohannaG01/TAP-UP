package com.johannag.tapup.users.infrastructure.db.adapter;

import com.johannag.tapup.configurations.UserSystemConfig;
import com.johannag.tapup.users.domain.mappers.UserDomainMapper;
import com.johannag.tapup.users.domain.models.UserModel;
import com.johannag.tapup.users.infrastructure.db.entities.UserEntity;
import com.johannag.tapup.users.infrastructure.db.repositories.JpaUserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;
    private final UserDomainMapper userDomainMapper;
    private final UserSystemConfig userSystemConfig;

    @Override
    public boolean userExists(String email) {
        return jpaUserRepository.existsByEmail(email);
    }

    @Override
    @Transactional
    public void create(UserModel userModel) {
        UserEntity userEntityToBeSaved = userDomainMapper.toEntity(userModel);
        userEntityToBeSaved.setCreatedBy(userSystemConfig.getAdminUserId());
        userEntityToBeSaved.setUpdatedBy(userSystemConfig.getAdminUserId());

        UserEntity savedUserEntity = jpaUserRepository.save(userEntityToBeSaved);
        savedUserEntity.setCreatedBy(savedUserEntity.getId());
        savedUserEntity.setUpdatedBy(savedUserEntity.getId());

        jpaUserRepository.save(savedUserEntity);
    }
}
