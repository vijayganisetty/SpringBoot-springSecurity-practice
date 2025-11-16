package com.spring.practice.springSecurity;

import com.spring.practice.springSecurity.entity.UserEntity;
import com.spring.practice.springSecurity.service.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringSecurityApplicationTests {


	@Autowired
	private JwtService jwtService;


	@Test
	void contextLoads() {

		UserEntity user = new UserEntity(4L, "vijay@gmail.com", "1234");

		String token = jwtService.generateToken(user);

		System.out.println(token);

		System.out.println(" Id of user"+ jwtService.getUserIdFromToken(token));
	}




}
