package com.imaginea.gr.service;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LsRemoteCommand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/applicationContext.xml"})
public class TestContestService {

	@Autowired
	ContentService contentService; 
	@Test
	public void getRepoInfoTest()
	{
		/*try{
			String url = "https://github.com/schacon/grit";
					//"https://github.com/umamaheswaraa";
			String str = contentService.getRepoInfo(url);
			 System.out.println("STRING :"+str);
		}catch (Exception e) {
			// TODO: handle exception
		}*/
		
	}
	@Test
	public void getRepoInfoTestApi()
	{
		/*try{
			System.out.println("test API");
			String url = "https://api.github.com/users/schacon/repos";
					//"https://api.github.com/users/umamaheswaraa";
			String str = contentService.getRepoInfo(url);
			 System.out.println("STRING :"+str);
		}catch (Exception e) {
			// TODO: handle exception
		}*/
		
		/*
		git://github.com/blynn/gitmagic.git
		git://gitorious.org/gitmagic/mainline.git
		https://code.google.com/p/gitmagic/
		git://git.assembla.com/gitmagic.git
		git@bitbucket.org:blynn/gitmagic.git
		
			//String url ="git@github.com:companyName/ProjectName.git";
			//String url ="git@github.com:umamaheswaraa/CRUD-App.git";
			//String url ="https://demo-gitblit.rhcloud.com/git/gitblit.git";
			 * 
			 * 
		*
		*/
		
	}
	
	@Test 
	public void getContentWithNullUserName(){
		System.out.println("Test Get Content");
		try{
			
			String searchUrl = "git@github.com:umamaheswaraa/CRUD-App.git";
			contentService.getReposotoryContent(searchUrl,null);
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Test 
	public void getContentWithUserName(){
		System.out.println("Test Get Content");
		try{
			String searchUrl = "git@github.com:umamaheswaraa/CRUD-App.git";
			contentService.getReposotoryContent(searchUrl,"akasam");
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Test 
	public void getFolderContent(){
		System.out.println("Test Folder Content");
		try{
			String path = "/users/umamaheswaraa/git/CRUD-App/src";
			contentService.getFolderContent(path);
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Test 
	public void getRemoteRepo(){
		System.out.println("Test Get Content");
		try{
			contentService.getRemoteRepository();
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
}
