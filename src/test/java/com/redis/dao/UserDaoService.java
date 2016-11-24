package com.redis.dao;

import org.springframework.stereotype.Repository;

import com.redis.entry.User;

@Repository
public class UserDaoService {

	public User getUser() {
		System.out.println("jdbc获取数据");
		return null;
	}

	public int insert(User user) {
		System.out.println("jdbc插入数据");
		return 0;
	}

	public int update(User user) {
		System.out.println("jdbc修改数据");
		return 0;
	}

	public int delete(String name) {
		System.out.println("jdbc删除数据");
		return 0;
	}

}
