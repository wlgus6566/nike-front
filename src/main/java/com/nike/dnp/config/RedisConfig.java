package com.nike.dnp.config;

import com.nike.dnp.common.viriable.Redis;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
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
 * @since 2020.05.21
 * @author 오지훈
 * @Description Redis Config
 * @history [오지훈] [2020.05.21] [최초 작성]
 */
@Configuration
public class RedisConfig extends CachingConfigurerSupport {

	/**
	 * JedisPoolConfig 빈
	 * 
	 * @return JedisPoolConfig
	 */
	@Bean
	public JedisPoolConfig jedisPoolConfig() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxIdle(30);
		jedisPoolConfig.setMinIdle(10);
		jedisPoolConfig.setTestOnBorrow(true);
		jedisPoolConfig.setTestOnReturn(true);
		return jedisPoolConfig;
	}

	/**
	 * JedisConnectionFactory 빈
	 * 
	 * @return JedisConnectionFactory
	 */
	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		RedisSentinelConfiguration standaloneConfiguration = new RedisSentinelConfiguration();
		standaloneConfiguration.sentinel(Redis.GLOBAL_REDIS_HOST, Redis.GLOBAL_REDIS_PORT);
		/*setHostName();
		standaloneConfiguration.setPort();*/

		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(standaloneConfiguration, jedisPoolConfig());
		/*jedisConnectionFactory.setHostName(Redis.GLOBAL_REDIS_HOST);
		jedisConnectionFactory.setPort(Redis.GLOBAL_REDIS_PORT);
		jedisConnectionFactory.setUsePool(true);*/
		return jedisConnectionFactory;
	}

	/**
	 * RedisTemplate 빈
	 * 
	 * @return RedisTemplate
	 */
	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> template = new RedisTemplate<>();
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
	 * @return RedisCacheManager
	 */
	@Bean
	@Override
	public CacheManager cacheManager() {
		RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory());
		RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
				.entryTtl(Duration.ofSeconds(60 * 60 * 24 * 30));
		builder.cacheDefaults(defaultConfig);
		return builder.build();
	}
}
