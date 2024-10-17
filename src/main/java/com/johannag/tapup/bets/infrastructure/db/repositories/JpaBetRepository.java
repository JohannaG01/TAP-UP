package com.johannag.tapup.bets.infrastructure.db.repositories;

import com.johannag.tapup.bets.infrastructure.db.entities.BetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaBetRepository extends JpaRepository<BetEntity, Long> {
}