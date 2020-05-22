package com.nike.dnp.config;

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
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(jedisPoolConfig());
		/*jedisConnectionFactory.setHostName(GlobalVariable.GLOBAL_REDIS_HOST);
		jedisConnectionFactory.setPort(GlobalVariable.GLOBAL_REDIS_PORT);
		jedisConnectionFactory.setPassword(GlobalVariable.GLOBAL_REDIS_PASSWORD);*/
		jedisConnectionFactory.setUsePool(true);
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

		// 값은 json 문자열로 넣는다. @class 필드로 클래스 정보가 들어간다.
		RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
				.entryTtl(Duration.ofSeconds(60 * 60 * 24 * 30));

		builder.cacheDefaults(defaultConfig);
		return builder.build();
	}
}
