package ir.aio.qr.qrloginhandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class QrSessionConfiguration {

    @Bean
    ReactiveRedisOperations<String, QrSession> redisOperations(ReactiveRedisConnectionFactory factory) {
        Jackson2JsonRedisSerializer<QrSession> serializer = new Jackson2JsonRedisSerializer<>(QrSession.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String, QrSession> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

        RedisSerializationContext<String, QrSession> context = builder.value(serializer).build();

        return new ReactiveRedisTemplate<>(factory, context);
    }

}
