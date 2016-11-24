package com.redis.aop.advice;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;

import com.redis.annotation.AnnotationConstants;
import com.redis.annotation.ParameterValueKeyProvider;

/**
 * 
 * @author: zyw
 * @date: 2016年11月18日
 * @Description: 切面基类
 *
 */
public abstract class CacheAdvice {

	public static final String ENABLE_CACHE_PROPERTY = "redis.cache.enable";

	public static final String ENABLE_CACHE_PROPERTY_VALUE = "true";

	protected abstract Logger getLogger();

	/**
	 * 缓存 是否开启
	 * 
	 * @return
	 */
	protected boolean isEnable() {
		return ENABLE_CACHE_PROPERTY_VALUE.equals(System.getProperty(ENABLE_CACHE_PROPERTY));
	}

	/**
	 * 
	 * @author : zyw
	 * @date : 2016年11月18日 上午10:56:16
	 * @Description : 获取方法或者方法的参数
	 */

	@SuppressWarnings("rawtypes")
	protected Method getMethod(ProceedingJoinPoint pjp) {
		// 获取参数的类型
		Object[] args = pjp.getArgs();
		Class[] argTypes = new Class[pjp.getArgs().length];
		for (int i = 0; i < args.length; i++) {
			argTypes[i] = args[i].getClass();
		}
		Method method = null;
		try {
			method = pjp.getTarget().getClass().getMethod(pjp.getSignature().getName(), argTypes);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return method;
	}

	/**
	 * 
	 * @author : zyw
	 * @date : 2016年11月18日 上午10:55:53
	 * @Description : 获取缓存的key
	 */
	protected String getCacheKey(String namespace, String assignedKey, Annotation[][] ans, Object[] args) {
		StringBuilder sb = new StringBuilder();
		// 拼接命名空间
		if (namespace != null && !namespace.equals("") && !namespace.equals(AnnotationConstants.DEFAULT_STRING)) {
			sb.append(namespace);
			sb.append("_");
		}
		// 拼接key
		if (assignedKey != null && !assignedKey.equals("") && !assignedKey.equals(AnnotationConstants.DEFAULT_STRING)) {
			sb.append(assignedKey);
			sb.append("_");
		}
		// 拼接参数
		if (ans != null && ans.length > 0) {
			for (Annotation[] anitem : ans) {
				// 方知 注解过多 以此提前出循环
				if (anitem.length > 0 && anitem[0] instanceof ParameterValueKeyProvider) {
					for (Annotation an : anitem) {
						ParameterValueKeyProvider vk = (ParameterValueKeyProvider) an;
						Object object = args[vk.order()];
						if (this.isType(object)) {
							sb.append(object + "_");
						} else {
							// 反射获取参数
							Field[] field = object.getClass().getDeclaredFields();
							// 遍历
							for (Field file : field) {
								// 如果参数跟key一直则拼接参数
								if (file.getName().equals(assignedKey)) {
									try {
										// 修改访问权限
										file.setAccessible(true);
										// 拼接参数
										sb.append(file.get(object) + "_");
									} catch (IllegalArgumentException e) {
										e.printStackTrace();
									} catch (IllegalAccessException e) {
										e.printStackTrace();
									}

								}
							}
						}
					}
					break;
				}
			}
		}

		return sb.toString();
	}

	/**
	 * 
	 * @author : zyw
	 * @date : 2016年11月18日 上午10:53:31
	 * @Description : 获取缓存的value
	 */
	protected Object getCacheValue(Annotation[][] ans, Object[] args) {
		Object object = null;
		if (ans != null && ans.length > 0) {
			for (Annotation[] anitem : ans) {
				// 方知 注解过多 以此提前出循环
				if (anitem.length > 0 && anitem[0] instanceof ParameterValueKeyProvider) {
					for (Annotation an : anitem) {
						ParameterValueKeyProvider vk = (ParameterValueKeyProvider) an;
						object = args[vk.order()];
					}
				}
			}
		}
		return object;
	}

	/**
	 * 
	 * @author : zyw
	 * @date : 2016年11月18日 上午10:52:41
	 * @Description : 判断类型
	 */
	protected boolean isType(Object object) {
		if (object.getClass().isPrimitive()) {
			return true;
		} else if (object instanceof String) {
			return true;
		} else if (object instanceof Integer) {
			return true;
		} else if (object instanceof Boolean) {
			return true;
		} else if (object instanceof Double) {
			return true;
		} else if (object instanceof Float) {
			return true;
		} else if (object instanceof Long) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @author : zyw
	 * @date : 2016年11月18日 上午10:52:30
	 * @Description : 判断类型
	 */
	protected boolean isType(Class<?> classes) {
		String method = classes.getName();
		// 无返回类型
		if (method.equals("void")) {
			return false;
		} else if (classes.isPrimitive()) {
			// 返回基础类型
			return false;
		} else if (method.equals("String") || method.equals("Integer") || method.equals("Boolean") || method.equals("Double") || method.equals("Float") || method.equals("Long")) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 
	 * @author : zyw
	 * @date : 2016年11月18日 下午4:00:19
	 * @Description : 合并两个object 不包括基础类型以及引用类型
	 * @return 合并obj2参数到obj1
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	protected Object merge(Object obj1, Object obj2) throws IllegalArgumentException, IllegalAccessException {
		// 判断是不是基础类型或者引用类型
		if (!this.isType(obj1) && !this.isType(obj2)) {
			Object obj3 = null;
			// 如果obj2是数组
			if (obj2.getClass().isArray()) {
				Object obj4[] = (Object[]) obj2;
				obj3 = obj4[0];
			}
			// 判断两个Object是否是同一个实体
			if (obj1.getClass().equals(obj3.getClass())) {
				Field[] files = obj1.getClass().getDeclaredFields();
				for (Field field : files) {
					// 修改权限
					field.setAccessible(true);
					Field[] filess = obj1.getClass().getDeclaredFields();
					for (Field fields : filess) {
						if (field.getName().equals(fields.getName())) {
							// 修改权限
							fields.setAccessible(true);
							// 获取obj2参数
							Object valuess = fields.get(obj3);
							if (valuess != null) {
								// 设置obj1参数
								field.set(obj1, valuess);
							}
						}

					}
				}
				return obj1;
			} else {
				getLogger().info("Method Object types are not the same !");
			}
		} else {
			getLogger().info("Object type is not supported !");
		}
		return null;
	}

}
