package com.leaveword;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.leaveword.controller.UserController;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@AutoConfigureMockMvc
@WebAppConfiguration
public class DemoApplicationTest1 {
	   @Autowired
	    private UserController userController;
	 
	    private MockMvc mockMvc;
	    @Before
	    public void setup(){
	        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	    }
	    
	    //验证controller是否正常响应并打印返回结果
	    @Test
	    public void getUser() throws Exception {
	        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/getUser").accept(MediaType.APPLICATION_JSON)
	                .param("userId","1"))
	                .andExpect(MockMvcResultMatchers.status().isOk())
	                .andDo(MockMvcResultHandlers.print())
	                .andReturn();
	        System.out.println("输出 " + mvcResult.getResponse().getContentAsString());
	    }
	    
	    @Test
	    public void testUserLogin() throws Exception {
	        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/userLogin").accept(MediaType.APPLICATION_JSON)
	        		.param("userName", "xuhn")
	        		.param("userPassword", "1234"))
	        		.andExpect(MockMvcResultMatchers.status().isOk())
	                .andDo(MockMvcResultHandlers.print())
	                .andReturn();
	        
	        System.out.println("输出 " + mvcResult.getResponse().getContentAsString());
	    			     
	    }
	    
	    @Test
		@Transactional//测试环境下将自动进行回滚操作
		//@Rollback(false)
	    public void testUserRegist() throws Exception {
	        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/userRegister").accept(MediaType.APPLICATION_JSON)
	        		.param("userName", "xuhn5")
	        		.param("userPassword", "12345"))
	        		.andExpect(MockMvcResultMatchers.status().isOk())
	                .andDo(MockMvcResultHandlers.print())
	                .andReturn();
	        
	        System.out.println("输出 " + mvcResult.getResponse().getContentAsString());
	    			     
	    }
	 
	 
	

}
