package com.doubleo.entrancegateway.infra.config.redis;

import org.springframework.data.repository.CrudRepository;

public interface BlackListTokenRepository extends CrudRepository<BlackListToken, String> {}
