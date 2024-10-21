package com.epam.springcore.task.service.impl;


import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class LoginAttemptService {

    private final int MAX_ATTEMPTS = 3;
    private final long LOCK_TIME_DURATION = 5 * 60 * 1000;
    private final Map<String, Integer> attemptsCache = new ConcurrentHashMap<>();
    private final Map<String, Long> lockTimeCache = new ConcurrentHashMap<>();

    public void loginSucceeded(String key){
        attemptsCache.remove(key);
        lockTimeCache.remove(key);
    }

    public void loginFailed(String key){
        int attempts = attemptsCache.getOrDefault(key, 0);
        attempts++;
        attemptsCache.put(key, attempts);
        if (attempts >= MAX_ATTEMPTS){
            lockTimeCache.put(key, System.currentTimeMillis());
        }
    }

    public boolean isBlocked (String key){
        if (lockTimeCache.containsKey(key)){
            long lockTime = lockTimeCache.get(key);
            if (System.currentTimeMillis() - lockTime > LOCK_TIME_DURATION){
                attemptsCache.remove(key);
                lockTimeCache.remove(key);
                return false;
            }
            return true;
        }
        return false;
    }

}
