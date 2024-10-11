package com.johannag.tapup.horses.infrastructure.db.repositories;

import com.johannag.tapup.horses.infrastructure.db.entities.HorseEntity;
import com.johannag.tapup.horses.infrastructure.db.entities.HorseEntityState;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaHorseRepository extends JpaRepository<HorseEntity, Long> {

  /**
   * Checks if a horse entity exists with the specified code and state.
   *
   * @param code the unique code of the horse entity to check for
   * @param states a list of possible states for the horse entity
   * @return {@code true} if a horse entity with the specified code and one of the specified states exists,
   *         {@code false} otherwise
   */
  boolean existsByCodeAndStateIn(String code, List<HorseEntityState> states);

  /**
   * Finds a HorseEntity by its code with a pessimistic lock.
   *
   * @param code the code of the horse
   * @return an Optional containing the found HorseEntity, or empty if not found
   */
  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("SELECT h FROM HorseEntity h WHERE h.code = :code")
  Optional<HorseEntity> findMaybeOneByCode(String code);
}