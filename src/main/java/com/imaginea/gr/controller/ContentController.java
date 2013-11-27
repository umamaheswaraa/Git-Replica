package com.imaginea.gr.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imaginea.gr.service.ContentService;
import com.imaginea.gr.util.Constants;

@Controller
@RequestMapping(value="content")
public class ContentController {

	private static final Logger logger = LoggerFactory.getLogger(ContentController.class);
	
	@Autowired
	private ContentService contentService; 
	
	@RequestMapping(value="fetchInfo",method=RequestMethod.GET)
	public @ResponseBody String getContentInfo(@RequestParam("searchVal") String searchVal){
		String result=null;
		String userName=null;
		Map<String, Integer> map =null;
		String status="success";
		String projectName=null;
		try{
			projectName = contentService.getProjectName(searchVal);
			logger.info("projectName: "+projectName);
			if(projectName!=null){
				map = contentService.getReposotoryContent(searchVal,projectName);
			}
			userName = contentService.fetchUserInfo(searchVal);
		}catch (Exception e) {
			status = Constants.FAILED;
		}
			if(map==null || (map!=null && map.size()==0)){
				status = Constants.FAILED;
			}
			
			Map<String , String> objectMap = new HashMap<String, String>();
			objectMap.put("prePath", "");
			objectMap.put("userName", userName);
			objectMap.put("projectName", projectName);
			objectMap.put("status",status );

			JSONObject jo = new JSONObject();
			jo.put("data", map);	
			jo.put("otherData", objectMap);
			
			result = jo.toString();
 
		
		return result;
		
	}
	
	@RequestMapping(value="getNextSubFolderInfo",method=RequestMethod.GET)
	public @ResponseBody String getNextSubFolderInfo(@RequestParam("postData") String postData){
		String result=null;
		try{
			ObjectMapper mapper = new ObjectMapper();
			Map<String , String> dataMap = mapper.readValue(postData, java.util.HashMap.class);
			
			String prePath =dataMap.get("prePath");
			String subPath =dataMap.get("subPath");
			String userName =dataMap.get("userName");
			String path =dataMap.get("path");
			String projectName = dataMap.get("projectName");
			
			logger.info("projectName: "+projectName+" subPath :"+subPath);
			
			Map<String, Integer> map = contentService.getSubFolderDetails(path, prePath+subPath+"/");
			Map<String , String> objectMap = new HashMap<String, String>();
			prePath = prePath+subPath+"/";
			logger.info("prepath: "+prePath);
			objectMap.put("prePath",prePath);
			objectMap.put("userName", userName);
			objectMap.put("projectName", projectName);
			
			JSONObject jo = new JSONObject();
			jo.put("data", map);	
			jo.put("otherData", objectMap);
			result = jo.toString();
		}catch (Exception e) {
			// TODO: handle exception
		}
		return result;
		
	}
	@RequestMapping(value="getPreSubFolderInfo", method=RequestMethod.GET)
	public @ResponseBody String getPreSubFolderInfo(@RequestParam("postData") String postData){
		String result=null;
		String userName=null;	
		String projectName=null;
		Map<String, Integer> map=null;
		try{
			ObjectMapper mapper = new ObjectMapper();
			Map<String , String> dataMap = mapper.readValue(postData, java.util.HashMap.class);
			
			String prePath =dataMap.get("prePath");
			userName =dataMap.get("userName");
			String path =dataMap.get("path");
			projectName = dataMap.get("projectName");
			
			String destPath = contentService.getSubPath(prePath);
			if(userName==null || (userName!=null && userName.length()==0)){
				userName = contentService.fetchUserInfo(path);
			}
			if(projectName==null || (projectName!=null && projectName.length()==0))
			{
				projectName = contentService.getProjectName(path);
			}
			
			if(destPath!=null && destPath.length()>0)
			{
				map = contentService.getSubFolderDetails(path, destPath);
			}else{
				map = contentService.getReposotoryContent(path, projectName);
			}
			 
			Map<String , String> objectMap = new HashMap<String, String>();
			prePath = destPath;
			logger.info("prePath : "+prePath);
			objectMap.put("prePath",prePath);
			objectMap.put("userName", userName);
			objectMap.put("projectName", projectName);
			
			JSONObject jo = new JSONObject();
			jo.put("data", map);	
			jo.put("otherData", objectMap);
			result = jo.toString();
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		return result;
		
	}
	
	@RequestMapping(value="getBlobContent",method=RequestMethod.GET)
	public @ResponseBody String getStringContent(@RequestParam("postData") String postData){
		String result=null;
		String userName=null;
		String projectName=null;
		try{
			ObjectMapper mapper = new ObjectMapper();
			Map<String , String> dataMap = mapper.readValue(postData, java.util.HashMap.class);
			
			String prePath =dataMap.get("prePath");
			String subPath =dataMap.get("subPath");
			userName =dataMap.get("userName");
			String path =dataMap.get("path");
			projectName = dataMap.get("projectName");
			
			String content = contentService.getStringContent(path, prePath+subPath);
			JSONObject jo = new JSONObject();
			jo.put("Data", content);	
			if(prePath!=null && prePath.length()>0){
				jo.put("prePath", prePath+"/"+subPath);
			}else{
				jo.put("prePath", prePath+subPath);
			}
			
			jo.put("userName", userName);
			jo.put("projectName", projectName);
			
			result = jo.toString();
			
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}
	@RequestMapping(value="getListOfCommits",method=RequestMethod.GET)
	public @ResponseBody String getListOfCommit(@RequestParam("postData") String postData){
		String result=null;
		String userName=null;
		String projectName=null;

		try{
			ObjectMapper mapper = new ObjectMapper();
			Map<String , String> dataMap = mapper.readValue(postData, java.util.HashMap.class);
			
			userName =dataMap.get("userName");
			String path =dataMap.get("path");
			projectName = dataMap.get("projectName");
			Map<String, List<String>> map = contentService.getListOfCommits(path);
			JSONObject jo = new JSONObject();
			jo.put("data", map);	
			jo.put("userName", userName);
			jo.put("projectName", projectName);
			
			result = jo.toString();
				
				
		}catch (Exception e) {
			// TODO: handle exception
		}
			return result;
	}
	
	@RequestMapping(value="getListOfRemotes",method=RequestMethod.GET)
	public @ResponseBody String getListOfRemotes(@RequestParam("postData") String postData){
		String result=null;
		String userName=null;
		String projectName=null;

		try{
			ObjectMapper mapper = new ObjectMapper();
			Map<String , String> dataMap = mapper.readValue(postData, java.util.HashMap.class);
			
			userName =dataMap.get("userName");
			String path =dataMap.get("path");
			projectName = dataMap.get("projectName");
			List<String> list = contentService.getListOfRemotes(path);
			JSONObject jo = new JSONObject();
			jo.put("data", list);	
			jo.put("userName", userName);
			jo.put("projectName", projectName);
			
			result = jo.toString();
				
				
		}catch (Exception e) {
			// TODO: handle exception
		}
			return result;
	}
	
	@RequestMapping(value="getListOfTags",method=RequestMethod.GET)
	public @ResponseBody String getListOfTags(@RequestParam("postData") String postData){
		String result=null;
		String userName=null;
		String projectName=null;

		try{
			ObjectMapper mapper = new ObjectMapper();
			Map<String , String> dataMap = mapper.readValue(postData, java.util.HashMap.class);
			
			userName =dataMap.get("userName");
			String path =dataMap.get("path");
			projectName = dataMap.get("projectName");
			List<String> list = contentService.getListOfTags(path);
			JSONObject jo = new JSONObject();
			jo.put("data", list);	
			jo.put("userName", userName);
			jo.put("projectName", projectName);
			
			result = jo.toString();
				
				
		}catch (Exception e) {
			// TODO: handle exception
		}
			return result;
	}
	
}
