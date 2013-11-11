package com.imaginea.gr.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.GitCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imaginea.gr.entity.UserEntity;
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
			result = contentService.getReposotoryContent(searchVal,userName);
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		return result;
		
	}
	
	
}
