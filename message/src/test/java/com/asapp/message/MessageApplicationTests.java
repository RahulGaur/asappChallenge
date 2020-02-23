package com.asapp.message;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;

@SpringBootTest
class MessageApplicationTests {

	@Autowired
	Config config;

	@Test
	void contextLoads() {
	}

	@Configuration
	static class Config {

	}
}
