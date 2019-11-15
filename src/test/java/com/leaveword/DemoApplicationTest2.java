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

import com.leaveword.controller.WordController;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@AutoConfigureMockMvc
@WebAppConfiguration
public class DemoApplicationTest2 {
	@Autowired
	private WordController wordController;
	
    private MockMvc mockMvc;
    @Before
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(wordController).build();
    }
    
    @Test
    @Transactional//测试环境下将自动进行回滚操作
	//@Rollback(false)
    public void testleaveWord() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/leaveWord").accept(MediaType.APPLICATION_JSON)
        		.param("userId", "1")
        		.param("title", "Tim")
        		.param("content", "Hello! xuhn!"))
        		.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        
        System.out.println("输出 " + mvcResult.getResponse().getContentAsString());
    			     
    }
    
    @Test
    public void testgetWords() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/getWords").accept(MediaType.APPLICATION_JSON)
        		.param("userId", "1"))
        		.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        
        System.out.println("输出 " + mvcResult.getResponse().getContentAsString());
    			     
    }

}
