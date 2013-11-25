package com.imaginea.gr.controller;

import java.util.Map;

import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imaginea.gr.entity.Roles;
import com.imaginea.gr.entity.User;
import com.imaginea.gr.service.LoginService;
import com.imaginea.gr.util.Constants;

@RequestMapping("login")
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private LoginService loginService;

	@RequestMapping(value="getRoles", method=RequestMethod.GET)
	public @ResponseBody String getRoles(){
		
		String result =null;
		try{
			JSONObject jo = new JSONObject();
			Map<String, String> map = loginService.getRoles();
			jo.put("data", map);
			result = jo.toString();
		}catch (Exception e) {
		}
		return result;
	}
	
	@RequestMapping(value="verifyUser", method=RequestMethod.GET)
	public @ResponseBody String verifyLogin(@RequestParam("userData") String userData){
		String result="success";
		String userName=null;
		String errorMsg=null;
		JSONObject jo = new JSONObject();
		try{
			ObjectMapper mapper = new ObjectMapper();
			User user = new User();
			Map<String , String> dataMap = mapper.readValue(userData, java.util.HashMap.class);
			user.setUserName(dataMap.get("username"));
			user.setPassword(dataMap.get("password"));
			User retUser = loginService.getUser(user);
			userName = retUser.getUserName();
			logger.info("Verify User Method uerName: "+retUser.getUserName());
		}catch (Exception e) {
			result=Constants.FAILED;
			errorMsg = e.getMessage();
		}
		
		jo.put("result", result);
		jo.put("username", userName);
		jo.put("errorMsg", errorMsg);
		result = jo.toString();
		return result;
	}
	
	@RequestMapping(value="addUser", method=RequestMethod.POST)
	public @ResponseBody String addUser(@RequestParam("userData") String userData){
		String result="success";
		String userName=null;
		String errorMsg=null;
		try{
			ObjectMapper mapper = new ObjectMapper();
			User user = new User();
			Map<String , String> dataMap = mapper.readValue(userData, java.util.HashMap.class);
			user.setUserName(dataMap.get("username"));
			user.setPassword(dataMap.get("password"));
			user.setRole(dataMap.get("role"));
			
			userName = loginService.addUser(user);
			if(userName!=null && userName.equalsIgnoreCase(Constants.DUPLICATE)){
				result=Constants.DUPLICATE_MSG;
			}
				
			logger.info("uerName: "+user.getUserName());			
		}catch (Exception e) {
			result=Constants.FAILED;
			errorMsg = e.getMessage();
		}
		JSONObject jo = new JSONObject();
		jo.put("result", result);
		jo.put("username", userName);
		jo.put("errorMsg", errorMsg);
		result = jo.toString();
		return result;
	}
}
