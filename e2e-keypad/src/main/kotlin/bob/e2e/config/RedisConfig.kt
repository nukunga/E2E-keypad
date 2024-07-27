package bob.e2e.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories

@Configuration
@EnableRedisRepositories
class RedisConfig {

    @Bean
    fun redisConnectionFactory() = LettuceConnectionFactory()

    @Bean
    fun redisTemplate() = RedisTemplate<String, Any>().apply {
        setConnectionFactory(redisConnectionFactory())
    }
}