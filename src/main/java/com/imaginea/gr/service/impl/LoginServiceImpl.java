package com.imaginea.gr.service.impl;



import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.imaginea.gr.dao.Dao;
import com.imaginea.gr.entity.Entity;
import com.imaginea.gr.entity.Roles;
import com.imaginea.gr.entity.User;
import com.imaginea.gr.exception.GitReplicaException;
import com.imaginea.gr.service.LoginService;
import com.imaginea.gr.util.Constants;

/**
 * @author umamaheswaraa
 *
 */
public class LoginServiceImpl implements LoginService {

	private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

	private Dao<Entity, Long> genericDao;
	
	public Dao<Entity, Long> getGenericDao() {
		return genericDao;
	}

	public void setGenericDao(Dao<Entity, Long> genericDao) {
		this.genericDao = genericDao;
	}

	/**
	 * Save the new user record into DB
	 *@param user
	 *@return String
	 *@throws GitReplicaException
	 */
	@Transactional
	public String addUser(User user) throws GitReplicaException{
		String retStr="";
		try{
			User duplicateUser = this.getUser(user.getUserName());
			if(duplicateUser==null){
				genericDao.save(user);
				retStr=user.getUserName();				
			}else{
				retStr = Constants.DUPLICATE;
			}
			
		}catch (Exception e) {
			logger.error("Exception while Saving User :"+e.getMessage());
			throw new GitReplicaException("Exception when saving user in Service Mathod :"+e.getMessage());
		}
		return retStr;
	}
	
	/**
	 * Get the user record from DB 
	 *@param user
	 *@return User
	 *@throws GitReplicaException
	 */
	public User getUser(User user) throws GitReplicaException {
		User retUser=null;
		try{
			Hashtable<String, String> criteria = new Hashtable<String, String>();
			criteria.put("userName",user.getUserName());
			criteria.put("password",user.getPassword());
			retUser = genericDao.getEntity(User.class, "User.getUser", criteria);
		}catch (Exception e) {
			logger.error("Exception while fetching user info"+e.getMessage());
			throw new GitReplicaException("Exception while fetching the User :"+e.getMessage());
		}
		return retUser;
	}
	
	/**
	 * get the user from DB based on userName
	 *@param userName
	 *@return User
	 *@throws GitReplicaException
	 */
	public User getUser(String userName) throws GitReplicaException {
		User retUser=null;
		try{
			Hashtable<String, String> criteria = new Hashtable<String, String>();
			criteria.put("userName",userName);
			retUser = genericDao.getEntity(User.class, "User.getUserbyName", criteria);
		}catch(javax.persistence.NoResultException ne){
			logger.info("No records found for this user :"+userName);
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception while fetching user info"+e.getMessage());
			throw new GitReplicaException("Exception while fetching User info :"+e.getMessage());
		}
		return retUser;
	}
	
	/**
	 *get the List of Roles from enum
	 *@return Map<String,String>
	 *@throws GitReplicaException
	 */
	public Map<String, String> getRoles() throws GitReplicaException{
		Map<String, String> map = new HashMap<String, String>();
		Roles[] roles = Roles.values();
		int length = roles.length;
		
		for(int i=0;i< length;i++){
			roles[i].getValue();
			roles[i].name();
			map.put(roles[i].name(), roles[i].getValue());
			logger.info("Name: "+roles[i].name());
			logger.info("value: "+roles[i].getValue());
		}
		return map;
	}

}
