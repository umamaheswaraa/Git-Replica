package com.imaginea.gr.service;

import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.imaginea.gr.entity.User;
import com.imaginea.gr.exception.GitReplicaException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/applicationContext.xml"})
public class TestLoginService {

	@Autowired
	LoginService loginService; 
	
	@Test
	public void getRoles() throws GitReplicaException{	
		loginService.getRoles();
	}
	
	@Test
	public void save() throws GitReplicaException{
		User user = new User();
		
		user.setUserName("umamaheswar");
		user.setPassword("123");
		user.setRole("GUEST");
		
		String pkey = loginService.addUser(user);
		Assert.assertNotNull(pkey);
		
	}
	
	@Test(expected=GitReplicaException.class)
	public void saveNullUser() throws GitReplicaException{
		User user = new User();
		
		user.setUserName(null);
		user.setPassword("123");
		user.setRole("GUEST");
		
		loginService.addUser(user);
	}

	@Test(expected=Exception.class)
	public void saveNullPassqword() throws GitReplicaException{
		User user = new User();
		
		user.setUserName("User");
		user.setPassword(null);
		user.setRole("GUEST");
		
		loginService.addUser(user);
	}
	@Test(expected=Exception.class)
	public void saveNullRole() throws GitReplicaException{
		User user = new User();
		
		user.setUserName("umama");
		user.setPassword("123");
		user.setRole(null);
		
		loginService.addUser(user);
	}
	@Test(expected=GitReplicaException.class)
	public void saveNulls() throws GitReplicaException{
		User user = new User();
		
		user.setUserName(null);
		user.setPassword(null);
		user.setRole(null);
		
		loginService.addUser(user);
	}
	@Test
	public void testForDuplicate() throws GitReplicaException{
		User user = new User();
		
		user.setUserName("umamaheswar");
		user.setPassword("123");
		user.setRole("GUEST");
		
		loginService.addUser(user);
		
	}
	
	@Test
	public void getUserbyUserName() throws GitReplicaException{
		
		User user = loginService.getUser("umamaheswar");
		Assert.assertNotNull(user);
	}
	
	@Test(expected=GitReplicaException.class)
	public void getUserbyNullValues() throws GitReplicaException{
		User user = new User();
		user.setUserName(null);
		user.setPassword(null);
		User user1 = loginService.getUser(user);
		Assert.assertNotNull(user1);
	}
	
	@Test(expected=GitReplicaException.class)
	public void getUserbyNullName() throws GitReplicaException{
		User user = new User();
		user.setUserName(null);
		user.setPassword("123");
		User user1 = loginService.getUser(user);
		Assert.assertNotNull(user1);
	}
	
	@Test(expected=GitReplicaException.class)
	public void getUserbyNullPassword() throws GitReplicaException{
		User user = new User();
		user.setUserName("umamaheswar");
		user.setPassword(null);
		User user1 = loginService.getUser(user);
		Assert.assertNotNull(user1);
	}
	
	@Test(expected=Exception.class)
	public void getUserbyUser() throws GitReplicaException{
		User user = new User();
		user.setUserName("umamaheswar");
		user.setPassword("123");
		User user1 = loginService.getUser(user);
		Assert.assertNotNull(user1);
	}
	
	@Test(expected=GitReplicaException.class)
	public void getUser() throws GitReplicaException{
		User user = new User();
		user.setUserName("umdsfdsfamaheswar");
		user.setPassword("123");
		User user1 = loginService.getUser(user);
		Assert.assertNotNull(user1);
	}
}
