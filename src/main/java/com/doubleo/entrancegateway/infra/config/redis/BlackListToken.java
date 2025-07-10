package com.doubleo.entrancegateway.infra.config.redis;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@RedisHash("blacklistToken")
public class BlackListToken {

    @Id private final String token;

    @TimeToLive private final long ttl;

    @Builder(access = AccessLevel.PRIVATE)
    private BlackListToken(String token, long ttl) {
        this.token = token;
        this.ttl = ttl;
    }
}
