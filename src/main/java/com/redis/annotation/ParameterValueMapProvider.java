package com.redis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author: zyw
 * @date: 2016年11月21日
 * @Description: map参数
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface ParameterValueMapProvider {

	ParameterValue param() default ParameterValue.Map;
}
