package com.example.demo1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo1.repository.NewRepository;
import com.example.demo1.repository.UserRepository;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class Demo1ApplicationTests {
	@Autowired
	private NewRepository newRepository;
	@Test
	void contextLoads() {
	}
	@Test
	public void test() 
	{
		assertEquals(2, newRepository.countByCategory_id(2L));
	}
}
