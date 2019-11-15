package com.leaveword;

import com.alibaba.fastjson.JSON;
import com.leaveword.controller.UserController;
import com.leaveword.domain.User;
import com.leaveword.domain.Word;
import com.leaveword.repository.UserRepository;
import com.leaveword.repository.WordRepository;
import com.leaveword.service.UserService;
import com.leaveword.service.UserServiceImpl;
import com.leaveword.service.WordServiceImpl;
import com.leaveword.utils.CommonTools;
import com.leaveword.utils.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@AutoConfigureMockMvc
//@Transactional//测试环境下将自动进行回滚操作
public class DemoApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
	private UserRepository userRepository;

	@Autowired
	private WordRepository wordRepository;

	@Autowired
	private UserServiceImpl userServiceImpl;
	
	@Autowired
	private WordServiceImpl wordServiceImpl;

	//initialization
	private User user1 = new User();
	private Word word1 = new Word();

	public void initialization(){
		this.user1.setUserName("xuhn");
		this.user1.setUserPassword("0000");
		this.user1.setRegisterTime(CommonTools.getCurrentTime());
		this.user1 = userRepository.save(user1);
		this.word1.setUserId(user1.getUserId());
		this.word1.setTitle("title");
		this.word1.setContent("test words");
		this.word1.setLeaveTime(CommonTools.getCurrentTime());
		this.word1 = wordRepository.save(word1);
	}

	public void rollBack(){
		userRepository.deleteAll();
		wordRepository.deleteAll();
	}

	/**
	 * 现在对于UserServiceImpl进行单元测试
	 */
	//进行注册测试
	@Test
	//@Rollback(false)
	public void ResgisterTest() {
		initialization();
		//test
		Response response = userServiceImpl.userRegister("xuhn", "0000");
		assertEquals("-1", response.getStatus());
		
		Response response1 = userServiceImpl.userRegister("", "0000");
		assertEquals("-1", response1.getStatus());
		
		Response response2 = userServiceImpl.userRegister("xuhn1", "");
		assertEquals("-1", response2.getStatus());
		
		Response response3 = userServiceImpl.userRegister("xuhn2", "0000");
		assertEquals("0", response3.getStatus());

		rollBack();
	}
	//进行获取用户测试
	@Test
	public void getUserTest() {
		initialization();

		//test
		Response response1 = userServiceImpl.getUser(user1.getUserId());
		assertEquals("0", response1.getStatus());
		
		Response response2 = userServiceImpl.getUser(-1);
		
		assertEquals("-1", response2.getStatus());

		rollBack();
	}
	//进行用户登录测试
	@Test
	public void UserLoginTest() {
		initialization();
		//test
		Response response1 = userServiceImpl.userLogin("xuhn", "0000");
		assertEquals("0", response1.getStatus());
		
		Response response2 = userServiceImpl.userLogin("", "0000");
		assertEquals("-1", response2.getStatus());
		
		Response response3 = userServiceImpl.userLogin("xuhn", "");
		assertEquals("-1", response3.getStatus());
		
		Response response4 = userServiceImpl.userLogin("xuhn", "1234");
		assertEquals("-1", response4.getStatus());
		
		Response response5 = userServiceImpl.userLogin("xuhn3", "4321");
		assertEquals("-1", response5.getStatus());

		rollBack();
	}
	/**
	 *进行留言服务的测试 
	 */
	//getWords(Integer userId)测试
	@Test
	public void getWordsTest() {
		initialization();

		Response response1 = wordServiceImpl.getWords(user1.getUserId());
		assertNotEquals("没有留言", response1.getContent());

		Response response2 = wordServiceImpl.getWords(-1);
		assertNotEquals("没有留言", response2.getContent());
		
		rollBack();
	}
	//leaveWord(Integer userId, String title, String content)测试
	@Test
	//@Rollback(false)
	public void leaveWordTest() {
		initialization();

		Response response1 = wordServiceImpl.leaveWord(user1.getUserId(), "title2", "test words2");
		assertEquals("0", response1.getStatus());
		
		Response response2 = wordServiceImpl.leaveWord(user1.getUserId()-1, "title2", "test words2");
		assertEquals("-1", response2.getStatus());
		
		Response response3 = wordServiceImpl.leaveWord(user1.getUserId(), "", "test words2");
		assertEquals("-1", response3.getStatus());
		
		Response response4 = wordServiceImpl.leaveWord(user1.getUserId(), "title2", "");
		assertEquals("-1", response4.getStatus());

		rollBack();
	}
	

}
