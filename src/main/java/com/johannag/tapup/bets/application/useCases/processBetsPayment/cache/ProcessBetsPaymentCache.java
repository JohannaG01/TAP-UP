package com.johannag.tapup.bets.application.useCases.processBetsPayment.cache;

import com.johannag.tapup.bets.domain.models.BetPayoutsCache;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class ProcessBetsPaymentCache {
    private final Map<UUID, BetPayoutsCache> cache = new HashMap<>();

    @Cacheable(value = "processBetsForFinishedHorseRaceCache", key = "#key")
    public BetPayoutsCache get(UUID key) {
        return cache.get(key);
    }

    public BetPayoutsCache getOrDefault(UUID key) {
        return cache.getOrDefault(key, new BetPayoutsCache());
    }

    @CachePut(value = "processBetsForFinishedHorseRaceCache", key = "#key")
    public BetPayoutsCache update(UUID key, BetPayoutsCache value) {
        cache.put(key, value);
        return value;
    }

    @CacheEvict(value = "processBetsForFinishedHorseRaceCache", key = "#key")
    public void evict(UUID key) {
        cache.remove(key);
    }
}