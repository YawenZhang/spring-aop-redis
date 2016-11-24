package com.redis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author: zyw
 * @date: 2016年11月21日
 * @Description: 删除redis
 *
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface DeleteRedis {

	/**
	 * 
	 * @author : zyw
	 * @date : 2016年11月21日 上午10:54:00
	 * @Description : key前缀 key的命名规则为：nameSpace + assignedKey + 参数
	 */
	String nameSpace() default AnnotationConstants.DEFAULT_STRING;

	/**
	 * 
	 * @author : zyw
	 * @date : 2016年11月21日 上午10:54:57
	 * @Description : key
	 */
	String assignedKey() default AnnotationConstants.DEFAULT_STRING;

	/**
	 * 
	 * @author : zyw
	 * @date : 2016年11月21日 上午10:53:41
	 * @Description : 储存类型
	 */
	ReadCacheType cacheType() default ReadCacheType.String;

}
