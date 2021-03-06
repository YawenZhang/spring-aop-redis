package com.redis.aop.advice;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;

import com.redis.annotation.ReadCacheType;
import com.redis.annotation.UpdateRedis;
import com.redis.cache.RedisCacheService;

/**
 * 
 * @author: zyw
 * @date: 2016年11月18日
 * @Description: 更新
 *
 */
public abstract class UpdateRedisAdvice extends CacheAdvice {

	@Autowired
	private RedisCacheService redisCacheService;

	protected Object update(final ProceedingJoinPoint pjp) throws Throwable {
		// 验证 缓存 是否开启
		if (isEnable()) {
			getLogger().info("Cache disabled");
			return pjp.proceed();
		}

		Method method = getMethod(pjp);
		UpdateRedis cacheable = method.getAnnotation(UpdateRedis.class);

		final Signature sig = pjp.getSignature();
		final MethodSignature msig = (MethodSignature) sig;

		// 验证 方法缓存是否开启
		if (cacheable != null && cacheable.cacheEnable()) {
			// 类型为String
			if (cacheable.cacheType() == ReadCacheType.String) {
				// 获取 KEY规则
				String namespace = cacheable.nameSpace();
				String assignedKey = cacheable.assignedKey();
				Annotation[][] anns = method.getParameterAnnotations();
				// 获取key
				String key = getCacheKey(namespace, assignedKey, anns, pjp.getArgs());
				Object value = null;

				// 方法结束以后在进行操作
				value = pjp.proceed();
				// 是否单独更新某个参数
				if (cacheable.separateUpdate()) {
					Object values = redisCacheService.get(key, cacheable.valueClass());
					if (values != null) {
						Object param = pjp.getArgs();
						// 合并参数
						values = merge(values, param);
						if (values != null) {
							redisCacheService.set(key, values, cacheable.expireTime());
						}
					}
				} else {
					// 查看返回类型
					Class<?> returnType = msig.getReturnType();
					// 如果返回实体的话则使用该实体作为缓存数据
					if (isType(returnType)) {
						redisCacheService.set(key, value, cacheable.expireTime());
					} else {
						redisCacheService.set(key, getCacheValue(anns, pjp.getArgs()), cacheable.expireTime());
					}
				}
				return value;
			}else{
				getLogger().info("Method other types are not supported . Name {}", msig.getName());
				return pjp.proceed();
			}
		} else { // 方法 缓存没开启
			getLogger().info("Method cache disabled . Name {}", msig.getName());
			return pjp.proceed();
		}
	}

}
