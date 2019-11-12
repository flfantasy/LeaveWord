package com.leaveword;

import com.alibaba.fastjson.JSON;
import com.leaveword.controller.UserController;
import com.leaveword.domain.User;
import com.leaveword.service.UserService;
import com.leaveword.service.UserServiceImpl;
import com.leaveword.utils.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class DemoApplicationTests {

	@Autowired
	private UserServiceImpl userServiceImpl;

	@Test
	public void loginTest() {
	assertEquals("0", userServiceImpl.get123());
	}
}
