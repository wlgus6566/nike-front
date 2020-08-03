package com.nike.dnp.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

/**
 * Redis Config
 *
 * @author 오지훈
 * @since 2020. 6. 24. 오후 6:08:07
 * @implNote Redis Config
 * @history [오지훈] [2020.05.21] [최초 작성]
 * @since 2020.05.21
 */
@Configuration
@RequiredArgsConstructor
public class RedisConfig extends CachingConfigurerSupport {

	/**
	 * The constant redisHost.
	 *
	 * @author [오지훈]
	 */
	public static String redisHost;

	/**
	 * The constant redisPort.
	 *
	 * @author [오지훈]
	 */
	public static int redisPort;

	/**
	 * Sets redis host.
	 *
	 * @param redisHost the redis host
	 * @author [오지훈]
	 * @since 2020. 6. 24. 오후 6:08:07
	 * @implNote
	 */
	@Value("${spring.redis.host:}")
	public void setRedisHost(final String redisHost) {
		this.redisHost = redisHost;
	}

	/**
	 * Sets redis port.
	 *
	 * @param redisPort the redis port
	 * @author [오지훈]
	 * @since 2020. 6. 24. 오후 6:08:07
	 * @implNote
	 */
	@Value("${spring.redis.port:}")
	public void setRedisPort(final int redisPort) {
		this.redisPort = redisPort;
	}

	/**
	 * Jedis pool config jedis pool config.
	 *
	 * @return JedisPoolConfig jedis pool config
	 * @author [오지훈]
	 * @since 2020. 6. 24. 오후 6:08:07
	 * @implNote JedisPoolConfig 빈
	 */
	@Bean
	public JedisPoolConfig jedisPoolConfig() {
		final JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxIdle(30);
		jedisPoolConfig.setMinIdle(10);
		jedisPoolConfig.setTestOnBorrow(true);
		jedisPoolConfig.setTestOnReturn(true);
		return jedisPoolConfig;
	}

	/**
	 * Redis connection factory redis connection factory.
	 *
	 * @return JedisConnectionFactory redis connection factory
	 * @author [오지훈]
	 * @since 2020. 6. 24. 오후 6:08:07
	 * @implNote JedisConnectionFactory 빈
	 */
	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		final JedisConnectionFactory connectionFactory = new JedisConnectionFactory(jedisPoolConfig());
		connectionFactory.setHostName(redisHost);
		connectionFactory.setPort(redisPort);
		connectionFactory.setUsePool(true);
		return connectionFactory;
	}

	/**
	 * Redis template redis template.
	 *
	 * @return RedisTemplate redis template
	 * @author [오지훈]
	 * @since 2020. 6. 24. 오후 6:08:07
	 * @implNote RedisTemplate 빈
	 */
	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		final RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(redisConnectionFactory());
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		template.setHashKeySerializer(new GenericJackson2JsonRedisSerializer());
		template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
		template.setEnableDefaultSerializer(true);
		template.setEnableTransactionSupport(false);
		return template;
	}

	/**
	 * Redis CacheManager
	 *
	 * @return RedisCacheManager cache manager
	 * @author [오지훈]
	 * @since 2020. 6. 24. 오후 6:08:07
	 * @implNote
	 */
	@Bean
	@Override
	public CacheManager cacheManager() {
		final RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory());
		final RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
				.entryTtl(Duration.ofSeconds(60 * 60 * 24 * 30));
		builder.cacheDefaults(defaultConfig);
		return builder.build();
	}
}
