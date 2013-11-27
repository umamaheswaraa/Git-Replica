package com.imaginea.gr.controller;

import junit.framework.Assert;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.imaginea.gr.exception.GitReplicaException;
import com.imaginea.gr.service.LoginService;
import com.imaginea.gr.service.impl.LoginServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class TestLoginController {

	@Mock private LoginService loginService;
	
    private LoginController loginController;
	
	@Before
	public void setUp(){
		loginController = new LoginController();
		loginService = new LoginServiceImpl();
		ReflectionTestUtils.setField(loginController, "loginService", loginService);
	}
	
	@After
	public void tearDown() throws Exception{
		loginService=null;
		loginController=null;
	}
	
	@Test
	public void getRoles() throws GitReplicaException{
		
		String jsonString = loginController.getRoles();
		Assert.assertFalse(jsonString == null || jsonString.isEmpty());		
	}
	
	@Test
	public void getUser() throws GitReplicaException{
		JSONObject json = new JSONObject();
		json.put("username", "uma");
		json.put("password", "1234");
		String userData = json.toString();
		String jsonString = loginController.verifyLogin(userData);
		Assert.assertFalse(jsonString == null || jsonString.isEmpty());		
	}
	
	@Test
	public void getUserByUserName() throws GitReplicaException{
		JSONObject json = new JSONObject();
		json.put("username", "");
		json.put("password", "1234");
		String userData = json.toString();
		String jsonString = loginController.verifyLogin(userData);
		Assert.assertFalse(jsonString == null || jsonString.isEmpty());		
	}
}
