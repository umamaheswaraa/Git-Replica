package com.imaginea.gr.service;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LsRemoteCommand;
import org.eclipse.jgit.treewalk.filter.PathFilter;
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
			contentService.getReposotoryContent(searchUrl,null,"CRUD-App");
		}catch (Exception e) {
			// TODO: handle exception
		}
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
	
	@Test
	public void getByteContent(){
		
		try{
			String path = "git@github.com:umamaheswaraa/CRUD-App.git";
			contentService.getByteContent(path, null);
			
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	@Test
	public void getFileAttributes(){
		//String path = "/users/umamaheswaraa/git/CRUD-App/";
		String path="git@github.com:umamaheswaraa/CRUD-App.git";
		contentService.getFileAttributes(path);
	}
	
	@Test
	public void getbrowseTree(){
		String path = "/users/umamaheswaraa/git/CRUD-App/";
		//contentService.browseTree(path,"src/main/resources",null);
	}
	
	@Test
	public void getSubFolderDetailsTest(){
		String path = "/users/umamaheswaraa/git/CRUD-App/";
		contentService.getSubFolderDetails(path,"src/main/resources/","null");
	}
	@Test
	public void TestgetSubPath(){
		String prepath="src/";
		contentService.getSubPath(prepath);
			
	}
	@Test
	public void getBlobContent(){
		String path = "git@github.com:umamaheswaraa/CRUD-App.git";
		String subpath="pom.xml";
		contentService.getBlobContent(path, subpath);
	}
	@Test
	public void getCommitBytes(){
		String path = "git@github.com:umamaheswaraa/CRUD-App.git";
		String subpath="pom.xml";
		contentService.getStringContent(path, subpath,null);
	}
	@Test
	public void getCommitBytesForJavaFile(){
		String path = "git@github.com:umamaheswaraa/CRUD-App.git";
		String subpath="src/main/java/com/imaginea/crud/controller/EmployeeController.java";
		contentService.getStringContent(path, subpath,null);
	}
	@Test
	public void getRepositoryConfigDetails(){
		String path = "git@github.com:umamaheswaraa/CRUD-App.git";
		
		contentService.fetchUserInfo(path, null);
	}
	@Test
	public void getListOfCommits(){
		String path = "git@github.com:umamaheswaraa/CRUD-App.git";
		
		contentService.getListOfCommits(path, null);
	}
	@Test
	public void getListOfRemotes(){
		//String path = "git@github.com:umamaheswaraa/CRUD-App.git";
		String path = "https://github.com/centic9/jgit-cookbook.git";
		contentService.getListOfRemotes(path, null);
	}
	@Test
	public void getListOfTags(){
		//String path = "git@github.com:umamaheswaraa/CRUD-App.git";
		String path = "https://github.com/centic9/jgit-cookbook.git";
		contentService.getListOfTags(path, null);
	}
	@Test
	public void getListOfContributors(){
		//String path = "git@github.com:umamaheswaraa/CRUD-App.git";
		String path = "https://github.com/centic9/jgit-cookbook.git";
		contentService.getListOfContributors(path, null);
	}
	@Test
	public void readRemoteRepo(){
		String path = "https://github.com/centic9/jgit-cookbook.git";
		contentService.listRemoteRepo(path, null);
	}
	
	
}
