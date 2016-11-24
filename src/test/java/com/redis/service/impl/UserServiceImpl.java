package com.redis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redis.annotation.DeleteRedis;
import com.redis.annotation.InsertRedis;
import com.redis.annotation.ParameterValueKeyProvider;
import com.redis.annotation.ReadCacheType;
import com.redis.annotation.ReadRedis;
import com.redis.annotation.UpdateRedis;
import com.redis.dao.UserDaoService;
import com.redis.entry.User;
import com.redis.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDaoService userDaoService;

	@Override
	@ReadRedis(namespace = "User", cacheType = ReadCacheType.String, assignedKey = "name", valueclass = User.class)
	public User getUser(@ParameterValueKeyProvider String name) {
		return userDaoService.getUser();
	}

	@Override
	@InsertRedis(nameSpace = "User", cacheType = ReadCacheType.String, assignedKey = "name", valueclass = User.class)
	public void insert(@ParameterValueKeyProvider User user) {
		userDaoService.insert(user);
	}

	@Override
	@UpdateRedis(nameSpace = "User", cacheType = ReadCacheType.String, assignedKey = "name", valueClass = User.class, separateUpdate = true)
	public int update(@ParameterValueKeyProvider User user) {
		return userDaoService.update(user);
	}

	@Override
	@DeleteRedis(nameSpace = "User", assignedKey = "name")
	public int delete(@ParameterValueKeyProvider String name) {
		return userDaoService.delete(name);
	}

}
