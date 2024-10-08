package com.johannag.tapup.users.infrastructure.db.repositories;

import com.johannag.tapup.users.infrastructure.db.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Checks if a user exists with the specified email.
     *
     * @param email the email to check for existence
     * @return {@code true} if a user with the given email exists, {@code false} otherwise
     */
    boolean existsByEmail(String email);
}
