package com.imaginea.gr.service;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.imaginea.gr.exception.GitReplicaException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/applicationContext.xml"})
public class TestContestService {

	@Autowired
	ContentService contentService; 
	
	@Test 
	public void getContentWithNullUserName() throws GitReplicaException{
		System.out.println("Test Get Content");
			
		String searchUrl = "git@github.com:umamaheswaraa/CRUD-App.git";
		contentService.getReposotoryContent(searchUrl,null,"CRUD-App");
		
	}
	
	@Test 
	public void getContentWithUserName(){
		System.out.println("Test Get Content");
		try{
			String searchUrl = "git@github.com:umamaheswaraa/CRUD-App.git";
			contentService.getReposotoryContent(searchUrl,"akasam","CRUD-App");
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Test
	public void getProjectNameWithNull(){
		System.out.println("getProjectNameWithNull :");
			//contentService.getProjectName(null);
	}
	@Test
	public void getProjectName(){
		System.out.println("getProjectName :");
			//contentService.getProjectName("git@github.com:umamaheswaraa/CRUD-App.git");
	}
	@Test
	public void getProjectNameWithNoDot(){
		System.out.println("getProjectNameWIthNoDot :");
			//contentService.getProjectName("git@github.com:umamaheswaraa/CRUD-App");
	}
	
	@Test
	public void getbrowseTree(){
		String path = "/users/umamaheswaraa/git/CRUD-App/";
		//contentService.browseTree(path,"src/main/resources",null);
	}
	
	@Test
	public void getSubFolderDetailsTest(){
		String path = "/users/umamaheswaraa/git/CRUD-App/";
		try {
			contentService.getSubFolderDetails(path,"src/main/resources/","null");
		} catch (GitReplicaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void TestgetSubPath(){
		String prepath="src/";
		try {
			contentService.getSubPath(prepath);
		} catch (GitReplicaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	@Test
	public void getCommitBytes(){
		String path = "git@github.com:umamaheswaraa/CRUD-App.git";
		String subpath="pom.xml";
		try {
			contentService.getStringContent(path, subpath,null);
		} catch (GitReplicaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void getCommitBytesForJavaFile(){
		String path = "git@github.com:umamaheswaraa/CRUD-App.git";
		String subpath="src/main/java/com/imaginea/crud/controller/EmployeeController.java";
		try {
			contentService.getStringContent(path, subpath,null);
		} catch (GitReplicaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void getRepositoryConfigDetails(){
		String path = "git@github.com:umamaheswaraa/CRUD-App.git";
		
		try {
			contentService.fetchUserInfo(path, null);
		} catch (GitReplicaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void getListOfCommits(){
		String path = "git@github.com:umamaheswaraa/CRUD-App.git";
		
		try {
			contentService.getListOfCommits(path, null);
		} catch (GitReplicaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void getListOfRemotes(){
		//String path = "git@github.com:umamaheswaraa/CRUD-App.git";
		String path = "https://github.com/centic9/jgit-cookbook.git";
		try {
			contentService.getListOfRemotes(path, null);
		} catch (GitReplicaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void getListOfTags(){
		//String path = "git@github.com:umamaheswaraa/CRUD-App.git";
		String path = "https://github.com/centic9/jgit-cookbook.git";
		try {
			contentService.getListOfTags(path, null);
		} catch (GitReplicaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void getListOfContributors(){
		//String path = "git@github.com:umamaheswaraa/CRUD-App.git";
		String path = "https://github.com/centic9/jgit-cookbook.git";
		try {
			contentService.getListOfContributors(path, null);
		} catch (GitReplicaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
}
