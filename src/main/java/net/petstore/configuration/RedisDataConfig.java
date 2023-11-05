package net.petstore.configuration;


import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;


@Configuration
@EnableRedisRepositories
public class RedisDataConfig {

}
