package com.imaginea.gr.dao;

import static org.mockito.Mockito.when;

import java.util.Hashtable;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.DataAccessException;

import com.imaginea.gr.entity.Entity;
import com.imaginea.gr.entity.User;

@RunWith(MockitoJUnitRunner.class)
public class TestGenericJpaDao {

	private GenericJpaDao<Entity, Long> genericDao;
	@Mock private EntityManager entityManager;
	
	@Mock
	private Query query;
	
	@Before
	public void setUp() throws Exception{
		genericDao = new GenericJpaDao<Entity, Long>();
		genericDao.setEntityManager(entityManager);
		
	}
	
	@After
	public void tearDown() throws Exception{
		
	}
	@Test
	public void saveTest(){
		User user = new User();
		user.setUserName("uma2");
		user.setPassword("ka");
		user.setRole("ADMIN");
		Long pkey = genericDao.save(user);
		Assert.assertNull(pkey);
		genericDao.delete(user);
	}
	
	@Test(expected=DataAccessException.class)
	public void saveTestUserNull(){
		User user = new User();
		user.setUserName(null);
		user.setPassword("ka");
		user.setRole("ADMIN");
		Long pkey = genericDao.save(user);
		Assert.assertNull(pkey);
	}
	
	@Test(expected=DataAccessException.class)
	public void saveTestRoleNull(){
		User user = new User();
		user.setUserName("Uma");
		user.setPassword("ka");
		user.setRole(null);
		Long pkey = genericDao.save(user);
		Assert.assertNull(pkey);
	}
	@Test(expected=DataAccessException.class)
	public void saveTestPasswordNull(){
		User user = new User();
		user.setUserName("uma");
		user.setPassword(null);
		user.setRole("ADMIN");
		Long pkey = genericDao.save(user);
		Assert.assertNull(pkey);
	}
	
	@Test(expected=DataAccessException.class)
	public void saveTestAllNull(){
		User user = new User();
		user.setUserName(null);
		user.setPassword(null);
		user.setRole(null);
		Long pkey = genericDao.save(user);
		Assert.assertNull(pkey);
	}
	@Test
	public void save(){
		User user = new User();
		user.setUserName("Uma");
		user.setPassword("kal");
		user.setRole("ADMIN");
		Long pkey = genericDao.save(user);
		Assert.assertNull(pkey);
	}
	
	@Test()
	public void TestDuplicateUser(){
		User user = new User();
		user.setUserName("Uma");
		user.setPassword("kal");
		user.setRole("ADMIN");
		Long pkey = genericDao.save(user);
		Assert.assertNull(pkey);
	}
	@Test
	public void findClass(){
		User user = new User();
		user.setUserName("Uma1");
		user.setPassword("kal");
		user.setRole("ADMIN");
		Long pkey = genericDao.save(user);
		Assert.assertNull(genericDao.find(User.class,pkey));
	}

	@Test
	public void getEntityForUser(){
		Hashtable<String, String> criteria = new Hashtable<String, String>();
		criteria.put("userName", "Uma");
		String sQuery = "select instance from User instance where instance.userName=:userName";
		when(entityManager.createNamedQuery(sQuery)).thenReturn(query);
		when(query.getSingleResult()).thenReturn(new User());
		
		Assert.assertNotNull(genericDao.getEntity(User.class,sQuery, criteria));
	}
	
	@Test
	public void getEntityForUserAndPassword(){
		Hashtable<String, String> criteria = new Hashtable<String, String>();
		criteria.put("userName", "Uma");
		criteria.put("password", "kal");
		String sQuery = "select instance from User instance where instance.userName=:userName and instance.password=:password";
		when(entityManager.createNamedQuery(sQuery)).thenReturn(query);
		when(query.getSingleResult()).thenReturn(new User());
		
		Assert.assertNotNull(genericDao.getEntity(User.class,sQuery, criteria));
	}
	
}
