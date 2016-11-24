package com.redis.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.redis.aop.advice.ReadRedisAdvice;

@Aspect
@Component
public class ReadRedisAop extends ReadRedisAdvice {
	
	private static final Logger LOG = LoggerFactory.getLogger(ReadRedisAop.class);

	@Pointcut("@annotation(com.redis.annotation.ReadRedis)")
	public void aspect() {

	}

	@Around("aspect()")
	public Object cacheSingleAssign(final ProceedingJoinPoint pjp) throws Throwable {
		getLogger().info("goto ReadRedis Method !");
		return cache(pjp);
	}

	@Override
	protected Logger getLogger() {
		return LOG;
	}

}
