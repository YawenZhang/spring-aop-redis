package com.redis.service;

import com.redis.entry.User;

public interface UserService {

	User getUser(String name);

	void insert(User user);
	
	int update(User user);
	
	int delete(String name);

}
