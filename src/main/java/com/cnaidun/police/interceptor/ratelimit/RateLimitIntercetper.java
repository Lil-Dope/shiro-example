package com.cnaidun.police.interceptor.ratelimit;

import com.cnaidun.police.util.BussinessException;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 统一处理限流操作
 * @Author dy
 * @Date 19:02 2019/05/07
 * @return
 **/
@Aspect
@Component
public class RateLimitIntercetper {
	Logger logger = LoggerFactory.getLogger(RateLimitIntercetper.class);
	private  ExpressionParser parser = new SpelExpressionParser();

	private RedisTemplate redisTemplate;

	@Autowired(required = false)
	public void setRedisTemplate(RedisTemplate redisTemplate) {
		RedisSerializer stringSerializer = new StringRedisSerializer();
		redisTemplate.setKeySerializer(stringSerializer);
		redisTemplate.setValueSerializer(stringSerializer);
		redisTemplate.setHashKeySerializer(stringSerializer);
		redisTemplate.setHashValueSerializer(stringSerializer);
		this.redisTemplate = redisTemplate;
	}

	@Pointcut("@annotation(com.cnaidun.police.interceptor.ratelimit.RateLimit)")
	public void rateLimit(){}

	@Before("rateLimit()")
	public void doBefore(JoinPoint point) {
		logger.info("进行限流操作---开始");
		MethodSignature signature = (MethodSignature) point.getSignature();
		Method method = signature.getMethod();
		RateLimit rateLimit = method.getAnnotation(RateLimit.class);
		//校验注解参数
		boolean hasLockName =  StringUtils.isEmpty(rateLimit.lockName());
		boolean hasKeys = rateLimit.keys() != null && rateLimit.keys().length > 0?true:false;
		if(hasLockName && hasKeys){
			throw  new BussinessException(500,"限流参数不正确");
		}
		//执行参数取值
		Object[]  args = point.getArgs();
		String[]  params = signature.getParameterNames();
		EvaluationContext ctx = new StandardEvaluationContext();
		if(params != null && params.length>0){
			String paramName = null;
			Object paramValue = null;
			for(int i=0;i<params.length;i++){
				paramName = params[i];
				paramValue = args[i];
				ctx.setVariable(paramName,paramValue);
			}
		}

		//限流key组装
		StringBuilder sb = new StringBuilder(rateLimit.lockName());
		for (String el: rateLimit.keys()){
			sb.append(":");
			Object obj = parser.parseExpression("#"+el).getValue(ctx);
			sb.append(obj);
			logger.info("限流参数:{}",obj);
		}
		String key = sb.toString();
		logger.info("限流key:{}",key);
		DefaultRedisScript defaultRedisScript = new DefaultRedisScript();
		defaultRedisScript.setResultType(Long.class);
		defaultRedisScript.setLocation(new ClassPathResource("lua/rateLimit.lua"));
		long result = (Long)redisTemplate.execute(defaultRedisScript,redisTemplate.getKeySerializer(),redisTemplate.getValueSerializer(), Arrays.asList(key), String.valueOf(rateLimit.limit()), String.valueOf(rateLimit.time()/1000));
		if (result == 0) {
			logger.warn("操作过快：{},{}",point.getTarget().getClass().getName(),signature.getMethod().getName());
			throw new BussinessException(500,"请求过快");
		}
		logger.info("进行限流操作---结束");
	}
}
