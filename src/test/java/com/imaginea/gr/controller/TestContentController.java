package com.imaginea.gr.controller;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.imaginea.gr.service.ContentService;
import com.imaginea.gr.service.impl.ContentServiceImpl;
import com.imaginea.gr.util.Utility;

@RunWith(MockitoJUnitRunner.class)
public class TestContentController {

	private ContentController contentController;
	private ObjectMapper mapper;
	@Mock private ContentService contentService;
	@Mock private Utility  utility;
	
	@Before
	public void setUp(){
		mapper = new ObjectMapper();
		contentController = new ContentController();
		ContentServiceImpl serviceImpl = new ContentServiceImpl();
		utility = new Utility();
		serviceImpl.setUtility(utility);
		contentService = serviceImpl;
		ReflectionTestUtils.setField(contentController, "contentService", contentService);
	}
	
	@Test
	public void getContent() throws Exception{
		String searchUrl = "git@github.com:umamaheswaraa/CRUD-App.git";
		String str = contentController.getContentInfo(searchUrl);
		Assert.assertNotNull(str);
		HashMap<String,HashMap<String, String>> map = this.getMapOfMapJSonObject(str); 
		Map<String , String> otherData = map.get("otherData");
		String status=otherData.get("status");
		Assert.assertNotNull(status);
	}
	
	@Test
	public void getContentWIthNull() throws Exception{
		String searchUrl = null;
		String str = contentController.getContentInfo(searchUrl);
		Assert.assertNotNull(str);
		HashMap<String,HashMap<String, String>> map = this.getMapOfMapJSonObject(str); 
		Map<String , String> otherData = map.get("otherData");
		String status=otherData.get("status");
		Assert.assertNotNull(status);
		Assert.assertEquals(status,"Failed");
	}
	
	@Test
	public void getListOfCommits() throws Exception{
		String searchUrl = "git@github.com:umamaheswaraa/CRUD-App.git";
		JSONObject json = new JSONObject();
		
		json.put("path", searchUrl);
		json.put("userName", "umamaheswaraa");
		json.put("projectName", "CRUD-App");
		String postData=json.toString();
		String str = contentController.getListOfCommit(postData);
		Assert.assertNotNull(str);
		Map<String, String> map = mapper.readValue(str, HashMap.class); 
		Object data = map.get("data");
		Assert.assertNotNull(data);
	}
	
	@Test
	public void getListOfCommitsForNull() throws Exception{
		String searchUrl = null;
		JSONObject json = new JSONObject();
		json.put("path", searchUrl);
		json.put("userName", "umamaheswaraa");
		json.put("projectName", "CRUD-App");
		String postData=json.toString();
		String str = contentController.getListOfCommit(postData);
		Assert.assertNull(str);
				
	}
	
	@Test
	public void getListOfRemotes() throws Exception{
		String searchUrl = "git@github.com:umamaheswaraa/CRUD-App.git";
		JSONObject json = new JSONObject();
		json.put("path", searchUrl);
		json.put("userName", "umamaheswaraa");
		json.put("projectName", "CRUD-App");
		String postData=json.toString();
		String str = contentController.getListOfRemotes(postData);
		Assert.assertNotNull(str);
		Map<String, String> map = mapper.readValue(str, HashMap.class); 
		Object data = map.get("data");
		Assert.assertNotNull(data);
	}
	
	@Test
	public void getListOfRemotesWithNull() throws Exception{
		String searchUrl = null;
		JSONObject json = new JSONObject();
		json.put("path", searchUrl);
		json.put("userName", "umamaheswaraa");
		json.put("projectName", "CRUD-App");
		String postData=json.toString();
		String str = contentController.getListOfRemotes(postData);
		Assert.assertNotNull(str);
		Map<String, String> map = mapper.readValue(str, HashMap.class); 
		Object data = map.get("data");
		Assert.assertNotNull(data);
	}
	
	@Test
	public void getListOfTags() throws Exception{
		String searchUrl = "git@github.com:umamaheswaraa/CRUD-App.git";
		JSONObject json = new JSONObject();
		json.put("path", searchUrl);
		json.put("userName", "umamaheswaraa");
		json.put("projectName", "CRUD-App");
		String postData=json.toString();
		String str = contentController.getListOfTags(postData);
		Assert.assertNotNull(str);
		Map<String, String> map = mapper.readValue(str, HashMap.class); 
		Object data = map.get("data");
		Assert.assertNotNull(data);
	}
	@Test
	public void getListOfTagsWithNull() throws Exception{
		String searchUrl = null;
		JSONObject json = new JSONObject();
		json.put("path", searchUrl);
		json.put("userName", "umamaheswaraa");
		json.put("projectName", "CRUD-App");
		String postData=json.toString();
		String str = contentController.getListOfRemotes(postData);
		Assert.assertNotNull(str);
		Map<String, String> map = mapper.readValue(str, HashMap.class); 
		Object data = map.get("data");
		Assert.assertNotNull(data);
	}
	
	@Test
	public void getNextSubFolder() throws Exception{
		String searchUrl = "git@github.com:umamaheswaraa/CRUD-App.git";
		JSONObject json = new JSONObject();
		json.put("path", searchUrl);
		json.put("subPath", "src");
		json.put("prePath", "");
		json.put("userName", "umamaheswaraa");
		json.put("projectName", "CRUD-App");
		String postData=json.toString();
		String str = contentController.getNextSubFolderInfo(postData);
		Assert.assertNotNull(str);
		Map<String, String> map = mapper.readValue(str, HashMap.class); 
		Object data = map.get("data");
		Assert.assertNotNull(data);
	}
	@Test
	public void getNextSubFolderNullPath() throws Exception{
		String searchUrl = null;
		JSONObject json = new JSONObject();
		json.put("path", searchUrl);
		json.put("userName", "umamaheswaraa");
		json.put("projectName", "CRUD-App");
		String postData=json.toString();
		String str = contentController.getListOfRemotes(postData);
		Assert.assertNotNull(str);
		Map<String, String> map = mapper.readValue(str, HashMap.class); 
		Object data = map.get("data");
		Assert.assertNotNull(data);
	}
	@Test
	public void getNextSubFolderForEmptySUbPath() throws Exception{
		String searchUrl = "git@github.com:umamaheswaraa/CRUD-App.git";
		JSONObject json = new JSONObject();
		json.put("path", searchUrl);
		json.put("subPath", "");
		json.put("prePath", "");
		json.put("userName", "umamaheswaraa");
		json.put("projectName", "CRUD-App");
		String postData=json.toString();
		String str = contentController.getNextSubFolderInfo(postData);
		Assert.assertNotNull(str);
		Map<String, String> map = mapper.readValue(str, HashMap.class); 
		Object data = map.get("data");
		Assert.assertNotNull(data);
	}
	@Test
	public void getPreFolderinfoWithPreEmpty() throws Exception{
		String searchUrl = "git@github.com:umamaheswaraa/CRUD-App.git";
		JSONObject json = new JSONObject();
		json.put("path", searchUrl);
		json.put("subPath", "uma");
		json.put("prePath", "");
		json.put("userName", "umamaheswaraa");
		json.put("projectName", "CRUD-App");
		String postData=json.toString();
		String str = contentController.getPreSubFolderInfo(postData);
		Assert.assertNotNull(str);
		Map<String, String> map = mapper.readValue(str, HashMap.class); 
		Object data = map.get("data");
		Assert.assertNotNull(data);
	}
	@Test
	public void getPreFolderinfo() throws Exception{
		String searchUrl = "git@github.com:umamaheswaraa/CRUD-App.git";
		JSONObject json = new JSONObject();
		json.put("path", searchUrl);
		json.put("subPath", "uma");
		json.put("prePath", "uma/");
		json.put("userName", "umamaheswaraa");
		json.put("projectName", "CRUD-App");
		String postData=json.toString();
		String str = contentController.getPreSubFolderInfo(postData);
		Assert.assertNotNull(str);
		Map<String, String> map = mapper.readValue(str, HashMap.class); 
		Object data = map.get("data");
		Assert.assertNotNull(data);
	}
	
	@Test
	public void getPreFolderinfoNullPath() throws Exception{
		String searchUrl =null;
		JSONObject json = new JSONObject();
		json.put("path", searchUrl);
		json.put("subPath", "uma");
		json.put("prePath", "uma/");
		json.put("userName", "umamaheswaraa");
		json.put("projectName", "CRUD-App");
		String postData=json.toString();
		String str = contentController.getPreSubFolderInfo(postData);
		Assert.assertNotNull(str);
		Map<String, String> map = mapper.readValue(str, HashMap.class); 
		Object data = map.get("data");
		Assert.assertNotNull(data);
	}
	@Test
	public void getStringContentWrongPath() throws Exception{
		String searchUrl = "git@github.com:umamaheswaraa/CRUD-App.git";
		JSONObject json = new JSONObject();
		json.put("path", searchUrl);
		json.put("subPath", "");
		json.put("prePath", "uma/");
		json.put("userName", "umamaheswaraa");
		json.put("projectName", "CRUD-App");
		String postData=json.toString();
		String str = contentController.getStringContent(postData);
		Assert.assertNotNull(str);
		Map<String, String> map = mapper.readValue(str, HashMap.class); 
		Object data = map.get("data");
		Assert.assertNull(data);
	}
	@Test
	public void getStringContnet() throws Exception{
		String searchUrl = "git@github.com:umamaheswaraa/CRUD-App.git";
		JSONObject json = new JSONObject();
		json.put("path", searchUrl);
		json.put("subPath", "pom.xml");
		json.put("prePath", "");
		json.put("userName", "umamaheswaraa");
		json.put("projectName", "CRUD-App");
		String postData=json.toString();
		String str = contentController.getPreSubFolderInfo(postData);
		Assert.assertNotNull(str);
		Map<String, String> map = mapper.readValue(str, HashMap.class); 
		Object data = map.get("data");
		Assert.assertNotNull(data);
	}
	@Test
	public void getStringContentForNUllPath() throws Exception{
		String searchUrl = null;
		JSONObject json = new JSONObject();
		json.put("path", searchUrl);
		json.put("subPath", "uma");
		json.put("prePath", "uma/");
		json.put("userName", "umamaheswaraa");
		json.put("projectName", "CRUD-App");
		String postData=json.toString();
		String str = contentController.getPreSubFolderInfo(postData);
		Assert.assertNotNull(str);
		Map<String, String> map = mapper.readValue(str, HashMap.class); 
		Object data = map.get("data");
		Assert.assertNotNull(data);
	}
	private HashMap<String,HashMap<String, String>> getMapOfMapJSonObject(String str) throws Exception{
		
		JsonFactory factory = new JsonFactory(); 
	    ObjectMapper mapper = new ObjectMapper(factory); 
	    
	    TypeReference<HashMap<String,HashMap<String, String>>> typeRef 
	          = new TypeReference<HashMap<String,HashMap<String, String>>>() {}; 
	    HashMap<String,HashMap<String, String>> map = mapper.readValue(str, typeRef);
	    return map;
	}
	
}
