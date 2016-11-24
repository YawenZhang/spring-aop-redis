package com.redis.aop.advice;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;

import com.redis.annotation.ReadRedis;
import com.redis.cache.RedisCacheService;

/**
 * 
 * @author: zyw
 * @date: 2016年11月18日
 * @Description: 读取
 *
 */
public abstract class ReadRedisAdvice extends CacheAdvice {

	@Autowired
	private RedisCacheService redisCacheService;

	protected Object cache(final ProceedingJoinPoint pjp) throws Throwable {
		// 验证 缓存 是否开启
		if (isEnable()) {
			return pjp.proceed();
		}

		Method method = getMethod(pjp);
		ReadRedis cacheable = method.getAnnotation(ReadRedis.class);
		Signature sig = pjp.getSignature();
		MethodSignature msig = (MethodSignature) sig;

		// 验证 方法缓存是否开启
		if (cacheable != null && cacheable.cacheEnable()) {
			// 获取 KEY规则
			String namespace = cacheable.namespace();
			String assignedKey = cacheable.assignedKey();
			Annotation[][] anns = method.getParameterAnnotations();
			// key
			String mapkey = getCacheKey(namespace, assignedKey, anns, pjp.getArgs());
			Object value = null;
			// 取redis数据
			value = redisCacheService.get(mapkey, cacheable.valueclass());
			if (value == null) {
				// 方法结束以后跳入
				value = pjp.proceed();
				if (value != null) {
					redisCacheService.set(mapkey, value, cacheable.expireTime());
				}
			}
			return value;

		} else { // 方法 缓存没开启
			getLogger().info("Method cache disabled . Name {}", msig.getName());
			return pjp.proceed();
		}
	}

}
