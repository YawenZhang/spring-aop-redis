package com.redis.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.redis.aop.advice.DeleteRedisAdvice;

@Aspect
@Component
public class DeleteRedisAop extends DeleteRedisAdvice {

	private static final Logger LOG = LoggerFactory.getLogger(DeleteRedisAop.class);

	@Pointcut("@annotation(com.redis.annotation.DeleteRedis)")
	public void before() {

	}

	@Around("before()")
	public Object cacheSingleAssign(final ProceedingJoinPoint jp) throws Throwable {
		getLogger().info("goto DeleteRedis Method !");
		return delete(jp);
	}

	@Override
	protected Logger getLogger() {
		return LOG;
	}

}