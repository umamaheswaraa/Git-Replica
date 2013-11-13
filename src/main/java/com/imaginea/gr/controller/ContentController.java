package com.imaginea.gr.controller;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imaginea.gr.service.ContentService;

@Controller
@RequestMapping(value="content")
public class ContentController {

	@Autowired
	ContentService contentService; 
	
	@RequestMapping(value="fetchInfo",method=RequestMethod.GET)
	public @ResponseBody String getContentInfo(@RequestParam("searchVal") String searchVal){
		String result=null;
		String userName=null;
		try{
			Map<String, Integer> map = contentService.getReposotoryContent(searchVal,userName);
			Map<String , String> objectMap = new HashMap<String, String>();
			objectMap.put("prePath", "");
			
			JSONObject jo = new JSONObject();
			jo.put("Data", map);	
			jo.put("otherData", objectMap);
			result = jo.toString();
 
		}catch (Exception e) {
			// TODO: handle exception
		}
		return result;
		
	}
	
	@RequestMapping(value="getNextSubFolderInfo",method=RequestMethod.GET)
	public @ResponseBody String getNextSubFolderInfo(@RequestParam("path") String path,@RequestParam("subPath") String subPath,@RequestParam("prePath") String prePath){
		String result=null;
		String userName=null;
		try{
			System.out.println("prePath "+prePath);
			System.out.println("subpath :"+subPath);
			
			Map<String, Integer> map = contentService.getSubFolderDetails(path, prePath+subPath+"/",userName);
			Map<String , String> objectMap = new HashMap<String, String>();
			prePath = prePath+subPath+"/";
			System.out.println("prePath : "+prePath);
			objectMap.put("prePath",prePath);
			
			JSONObject jo = new JSONObject();
			jo.put("Data", map);	
			jo.put("otherData", objectMap);
			result = jo.toString();
		}catch (Exception e) {
			// TODO: handle exception
		}
		return result;
		
	}
	@RequestMapping(value="getPreSubFolderInfo", method=RequestMethod.GET)
	public @ResponseBody String getPreSubFolderInfo(@RequestParam("path") String path, @RequestParam("prePath") String prePath){
		String result=null;
		String userName=null;	
		Map<String, Integer> map=null;
		try{
			System.out.println("prePath "+prePath);			
			String destPath = contentService.getSubPath(prePath);
			
			if(destPath!=null && destPath.length()>0)
			{
				map = contentService.getSubFolderDetails(path, destPath, userName);
			}else{
				map = contentService.getReposotoryContent(path, userName);
			}
			 
			Map<String , String> objectMap = new HashMap<String, String>();
			prePath = destPath;
			System.out.println("prePath : "+prePath);
			objectMap.put("prePath",prePath);
			
			JSONObject jo = new JSONObject();
			jo.put("Data", map);	
			jo.put("otherData", objectMap);
			result = jo.toString();
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		return result;
		
	}
	
	@RequestMapping(value="getContent",method=RequestMethod.GET)
	public @ResponseBody String getStringContent(@RequestParam("path") String path, @RequestParam("prePath") String prePath, @RequestParam("subPath") String subPath){
		String content=null;
		String userName=null;
		try{
			contentService.getStringContent(path, prePath+subPath,userName);
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		return content;
	}
	
	
	
	
}
