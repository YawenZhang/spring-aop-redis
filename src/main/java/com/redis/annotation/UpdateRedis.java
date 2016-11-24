package com.redis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author: zyw
 * @date: 2016年11月21日
 * @Description: 修改redis
 *
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UpdateRedis {

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
	String assignedKey() default AnnotationConstants.DEFAULT_KEY;

	/**
	 * 
	 * @author : zyw
	 * @date : 2016年11月21日 上午10:53:41
	 * @Description : 储存类型
	 */
	ReadCacheType cacheType() default ReadCacheType.String;

	/**
	 * 
	 * @author : zyw
	 * @date : 2016年11月21日 上午10:53:23
	 * @Description : 时间 0为永远
	 */
	int expireTime() default 0;

	/**
	 * 
	 * @author : zyw
	 * @date : 2016年11月21日 上午10:53:10
	 * @Description : 状态
	 */
	boolean cacheEnable() default true;

	/**
	 * 
	 * @author : zyw
	 * @date : 2016年11月21日 上午10:53:01
	 * @Description : 类型
	 */
	Class<?> valueClass() default String.class;

	/**
	 * 
	 * @author : zyw
	 * @date : 2016年11月21日 上午10:42:00
	 * @Description : 单独更新 单独修改某个参数
	 */
	boolean separateUpdate() default false;
}
