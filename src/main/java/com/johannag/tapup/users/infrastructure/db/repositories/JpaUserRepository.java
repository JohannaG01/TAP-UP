package com.johannag.tapup.users.infrastructure.db.repositories;

import com.johannag.tapup.users.infrastructure.db.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Checks if a user exists with the specified email.
     *
     * @param email the email to check for existence
     * @return {@code true} if a user with the given email exists, {@code false} otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Retrieves an {@link Optional} containing a {@link UserEntity} based on the provided email address.
     *
     * @param email The email address used to look up the user.
     * @return An {@link Optional} containing the {@link UserEntity} if a user is found, or an empty {@link Optional}
     * if no user exists with the given email.
     */
    Optional<UserEntity> findByEmail(String email);
}
