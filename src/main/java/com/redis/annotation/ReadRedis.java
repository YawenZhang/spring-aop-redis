package com.redis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author: zyw
 * @date: 2016年11月21日
 * @Description: 读取redis
 *
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ReadRedis {

	/**
	 * 
	 * @author : zyw
	 * @date : 2016年11月21日 上午10:55:56
	 * @Description : key前缀 key的命名规则为：nameSpace + assignedKey + 参数
	 */
	String namespace() default AnnotationConstants.DEFAULT_STRING;

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

	/**
	 * 
	 * @author : zyw
	 * @date : 2016年11月21日 上午10:53:23
	 * @Description : 时间 0为永远 -- 当取到的值为空时会进行插入
	 */
	int expireTime() default 0;

	/**
	 * 
	 * @author : zyw
	 * @date : 2016年11月21日 上午10:53:10
	 * @Description : 状态 -- 当取到的值为空时会进行插入
	 */
	boolean cacheEnable() default true;

	/**
	 * 
	 * @author : zyw
	 * @date : 2016年11月21日 上午10:53:01
	 * @Description : 类型
	 */
	Class<?> valueclass() default String.class;

}
