package me.roqb.opsdata.restservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class CacheService {

    @Autowired
    CacheManager cacheManager;

    public void evictAllCaches() {
        System.out.println("Evicting caches");
        cacheManager.getCacheNames().stream()
                .forEach(cacheName -> cacheManager.getCache(cacheName).clear());
    }

    /**
     * Clear caches every 10 minutes (600 x 1000ms)
     */
    @Scheduled(fixedRate = 600000)
    public void evictAllcachesAtIntervals() {
        evictAllCaches();
    }

}