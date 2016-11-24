package com.redis.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.redis.aop.advice.InsertRedisAdvice;

@Aspect
@Component
public class InsertRedisAop extends InsertRedisAdvice {

	private static final Logger LOG = LoggerFactory.getLogger(InsertRedisAop.class);

	@Pointcut("@annotation(com.redis.annotation.InsertRedis)")
	public void before() {
	}

	@Around("before()")
	public Object cacheSingleAssign(final ProceedingJoinPoint jp) throws Throwable {
		getLogger().info("goto InsertRedis Method !");
		return insert(jp);
	}

	@Override
	protected Logger getLogger() {
		return LOG;
	}

}