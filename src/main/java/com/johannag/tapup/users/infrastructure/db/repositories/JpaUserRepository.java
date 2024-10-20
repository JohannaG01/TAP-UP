package com.johannag.tapup.users.infrastructure.db.repositories;

import com.johannag.tapup.users.infrastructure.db.entities.UserEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Checks if a user exists with the specified email.
     *
     * @param email the email to check for existence
     * @return {@code true} if a user with the given email exists, {@code false} otherwise
     */
    boolean existsByEmail(String email);


    /**
     * Retrieves all users that exist in the system based on the provided UUIDs.
     *
     * @param userUuids a collection of UUIDs representing the users to check for existence
     * @return a list of UserEntity objects that were found in the database
     */
    List<UserEntity> findAllByUuidIn(Collection<UUID> userUuids);

    /**
     * Retrieves an {@link Optional} containing a {@link UserEntity} based on the provided email address.
     *
     * @param email The email address used to look up the user.
     * @return An {@link Optional} containing the {@link UserEntity} if a user is found, or an empty {@link Optional}
     * if no user exists with the given email.
     */
    Optional<UserEntity> findMaybeOneByEmail(String email);

    /**
     * Finds a {@link UserEntity} by its UUID.
     * This method searches the database for a user that matches the provided UUID.
     * If a user with the given UUID is found, it is returned wrapped in an {@link Optional}.
     * If no user is found, an empty {@link Optional} is returned.
     *
     * @param uuid the UUID of the user to be searched for
     * @return an {@link Optional} containing the found {@link UserEntity}, or empty if no user is found
     */
    Optional<UserEntity> findMaybeOneByUuid(UUID uuid);

    /**
     * Retrieves a user entity by its UUID with a pessimistic write lock.
     *
     * <p>This method is used to fetch a {@link UserEntity} instance from the database
     * while applying a pessimistic write lock. This ensures that no other transactions
     * can read or modify the user entity until the current transaction is completed,
     * thereby preventing concurrent modifications.</p>
     *
     * @param uuid the UUID of the user to retrieve
     * @return the {@link UserEntity} associated with the given UUID, or {@code null}
     * if no such user exists
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT u FROM UserEntity u WHERE u.uuid = :uuid")
    UserEntity findOneByUuidForUpdate(UUID uuid);

    /**
     * Retrieves a list of {@link UserEntity} objects with the specified UUIDs
     * while acquiring a pessimistic write lock on the selected rows.
     *
     * <p>This method is used to ensure that the retrieved user entities
     * cannot be modified by other transactions until the current transaction
     * is completed. This is particularly useful when updating user information
     * to prevent concurrent modifications.</p>
     *
     * @param uuid the UUID of the user entities to be retrieved.
     *             The method will return all user entities that match this UUID.
     * @return a list of {@link UserEntity} objects that match the specified UUID.
     *         If no entities are found, an empty list is returned.
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT u FROM UserEntity u WHERE u.uuid IN :uuid")
    List<UserEntity> findByUuidForUpdate(Collection<UUID> uuid);

    /**
     * Retrieves a {@link UserEntity} by its unique identifier (UUID).
     *
     * <p>This method is used to fetch a user entity from the database based on the provided UUID.</p>
     *
     * @param uuid the unique identifier of the user to be retrieved
     * @return the {@link UserEntity} associated with the given UUID, or null if not found
     */
    UserEntity findOneByUuid(UUID uuid);

    /**
     * Retrieves the user ID associated with the specified email address.
     *
     * <p>This method searches for a user in the system using their email address
     * and returns the unique identifier of the user if found. If no user is found
     * with the given email, the method will return {@code null}.</p>
     *
     * @param email the email address of the user to search for
     * @return the unique identifier of the user, or {@code null} if no user is found
     */

    @Query("SELECT u.id FROM UserEntity u WHERE u.email = :email")
    Long findIdByEmail(String email);

}
