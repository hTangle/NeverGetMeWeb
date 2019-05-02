package com.nevergetme.nevergetmeweb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds=10*24*3600)
public class RedisSessionConfig {
}
