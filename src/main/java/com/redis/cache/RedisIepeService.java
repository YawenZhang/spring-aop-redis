package com.redis.cache;

/**
 * 
 * @author: zyw
 * @date: 2016年6月29日
 * @Description: 依赖iepe-controller包
 *
 */
public interface RedisIepeService {

	/**
	 * 
	 * @author : zyw
	 * @date : 2016年6月29日 下午4:18:29
	 * @Description : 获取参数并转化基本类型
	 */
	<T> T getMapValue(String key, Class<T> t);

	/**
	 * 
	 * @author : zyw
	 * @date : 2016年6月29日 下午4:18:43
	 * @Description : 获取参数 json
	 */
	String getObjectValue(String key);

	/**
	 * 
	 * @author : zyw
	 * @date : 2016年6月29日 下午4:18:59
	 * @Description : 设置值
	 */
	void set(String key, Object value);

	/**
	 * 
	 * @author : zyw
	 * @date : 2016年6月29日 下午5:43:13
	 * @Description : 设置值-时间
	 */
	void set(String key, Object value, int seconds);

	/**
	 * 
	 * @author : zyw
	 * @date : 2016年7月1日 上午11:26:57
	 * @Description : 删除key
	 */
	void delete(String key);
}
