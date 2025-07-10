package com.doubleo.entrancegateway.infra.config.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlackListTokenService {
    private final BlackListTokenRepository blackListTokenRepository;

    public boolean existsByToken(String token) {
        return blackListTokenRepository.existsById(token);
    }
}
