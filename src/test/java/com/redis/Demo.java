package com.redis;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.redis.entry.User;
import com.redis.service.UserService;

public class Demo {
	public static void main(String[] args) throws InterruptedException {
		String[] iem = { "classpath:application-aop.xml", "classpath:application-propertyFile.xml", "classpath:application-redis.xml" };
		ApplicationContext act = new FileSystemXmlApplicationContext(iem);

		UserService userService = (UserService) act.getBean("userService");

		// 查询
//		 User a = userService.getUser("zhangsan");
		//
//		 System.out.println(a.getAddress());
//		 System.out.println(a.getAge());
//		 System.out.println(a.getName());

		User user = new User();
		user.setAddress("北京");
		user.setAge(22);
		user.setName("zhangsan");
		userService.insert(user);

//		user.setAddress(null);
//		user.setAge(120);
//		user.setName("zhangsan");
//		userService.update(user);

//		userService.delete("zhangsan");

	}

}
