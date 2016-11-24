package com.redis.cache.impl;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.redis.cache.RedisIepeService;
import com.redis.cache.util.gson.GsonBuilderUtil;

import redis.clients.jedis.BinaryJedis;

@Service
public class RedisIepeServiceImpl implements RedisIepeService {

	BinaryJedis binaryJedis = new BinaryJedis("192.168.1.33");

	private Gson gson = GsonBuilderUtil.create();

	@Override
	public <T> T getMapValue(String key, Class<T> t) {
		byte[] val = binaryJedis.get(key.getBytes());
		if (val != null) {
			String values = new String(val);
			return gson.fromJson(values, t);
		}
		return null;
	}

	@Override
	public String getObjectValue(String key) {
		byte[] val = binaryJedis.get(key.getBytes());
		if (val != null) {
			return new String(val);
		}
		return null;
	}

	@Override
	public void set(String key, Object value) {
		String jsonValue = gson.toJson(value);
		binaryJedis.set(key.getBytes(), jsonValue.getBytes());
	}

	@Override
	public void set(String key, Object value, int seconds) {
		String jsonValue = gson.toJson(value);
		binaryJedis.setex(key.getBytes(), seconds, jsonValue.getBytes());
	}

	@Override
	public void delete(String key) {
		binaryJedis.del(key.getBytes());
	}

}
