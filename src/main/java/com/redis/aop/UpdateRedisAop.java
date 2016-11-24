package com.redis.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.redis.aop.advice.UpdateRedisAdvice;

@Aspect
@Component
public class UpdateRedisAop extends UpdateRedisAdvice {

	private static final Logger LOG = LoggerFactory.getLogger(UpdateRedisAop.class);

	@Pointcut("@annotation(com.redis.annotation.UpdateRedis)")
	public void around() {

	}

	@Around("around()")
	public Object cacheSingleAssign(final ProceedingJoinPoint jp) throws Throwable {
		getLogger().info("goto UpdateRedis Method !");
		return update(jp);
	}

	@Override
	protected Logger getLogger() {
		return LOG;
	}

}