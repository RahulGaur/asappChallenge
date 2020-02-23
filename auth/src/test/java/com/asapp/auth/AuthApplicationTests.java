package com.asapp.auth;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootTest
class AuthApplicationTests {

	@Autowired
	Config config;

	@Test
	void contextLoads() {
	}

	@Configuration
	static class Config {

	}

}
