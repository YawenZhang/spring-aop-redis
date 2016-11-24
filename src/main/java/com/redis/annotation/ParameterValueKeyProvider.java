package com.redis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author: zyw
 * @date: 2016年11月21日
 * @Description: String类型参数
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface ParameterValueKeyProvider {

	/**
	 * 
	 * @author : zyw
	 * @date : 2016年11月21日 上午11:01:14
	 * @Description : 参数序号
	 */
	int order() default 0;

}