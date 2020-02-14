package com.cnaidun;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;


@SpringBootApplication
@ComponentScan(basePackages = {"com.cnaidun.police"})
@MapperScan({"com.cnaidun.police.mapper"})
@ImportResource(locations = { "classpath:druid-bean.xml" })
@EnableCaching
public class AidunSystemApplication {
	@Bean
	public Jackson2JsonRedisSerializer getJackson2JsonRedisSerializer() {
		return new Jackson2JsonRedisSerializer(Object.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(AidunSystemApplication.class, args);
	}
}
