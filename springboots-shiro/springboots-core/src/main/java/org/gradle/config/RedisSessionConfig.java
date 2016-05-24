package org.gradle.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
//@EnableRedisHttpSession  //启动redis保存session状态.
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 30) //1分钟失效
public class RedisSessionConfig{
	
}
