package com.redis.aop.advice;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;

import com.redis.annotation.DeleteRedis;
import com.redis.cache.RedisCacheService;

/**
 * 
 * @author: zyw
 * @date: 2016年11月18日
 * @Description: 删除
 *
 */
public abstract class DeleteRedisAdvice extends CacheAdvice {

	@Autowired
	private RedisCacheService redisCacheService;

	protected Object delete(final ProceedingJoinPoint pjp) throws Throwable {
		// 验证 缓存 是否开启
		if (isEnable()) {
			getLogger().info("Cache disabled");
			return pjp.proceed();
		}

		Method method = getMethod(pjp);
		// 获取注解
		DeleteRedis cacheable = method.getAnnotation(DeleteRedis.class);

		// 获取 KEY规则
		String namespace = cacheable.nameSpace();
		String assignedKey = cacheable.assignedKey();

		Annotation[][] anns = method.getParameterAnnotations();

		String mapkey = getCacheKey(namespace, assignedKey, anns, pjp.getArgs());
		redisCacheService.remove(mapkey);
		return pjp.proceed();
	}

}
