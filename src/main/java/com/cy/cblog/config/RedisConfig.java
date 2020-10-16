package com.cy.cblog.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class RedisConfig {

    @Value("${redis.key.prefix.authCode}")
    private String authCode;

    @Value("${redis.key.prefix.jwt}")
    private String jwt;

    @Value("${redis.key.expire.authCode}")
    private Long expire_authCode;

    @Value("${redis.key.expire.jwt}")
    private Long expire_jwt;

    @Value("${redis.key.expire.jwt-long}")
    private Long expire_jwt_long;

}
