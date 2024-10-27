package com.johannag.tapup.bets.application.cache;

import com.johannag.tapup.bets.domain.models.BetRefundsCache;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class BetsRefundCache {
    private final Map<UUID, BetRefundsCache> cache = new HashMap<>();

    @Cacheable(value = "processBetsRefundCache", key = "#key")
    public BetRefundsCache get(UUID key) {
        return cache.get(key);
    }

    @Cacheable(value = "processBetsPaymentCache", key = "#key")
    public BetRefundsCache getOrDefault(UUID key) {
        return cache.getOrDefault(key, new BetRefundsCache());
    }

    @CachePut(value = "processBetsRefundCache", key = "#key")
    public BetRefundsCache update(UUID key, BetRefundsCache value) {
        cache.put(key, value);
        return value;
    }

    @CacheEvict(value = "processBetsRefundCache", key = "#key")
    public void evict(UUID key) {
        cache.remove(key);
    }
}
