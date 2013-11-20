package com.imaginea.gr.service;

import java.util.List;
import java.util.Map;

import junit.framework.Assert;

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
	public void getContentAllCorrectValues() throws GitReplicaException{
		String searchUrl = "git@github.com:umamaheswaraa/CRUD-App.git";
		Map<String, Integer> map = contentService.getReposotoryContent(searchUrl,"CRUD-App");
		Assert.assertNotNull(map);
		Assert.assertTrue(map.size()>1);
	}
	
	@Test(expected=GitReplicaException.class)
	public void getContentWithWrongUrl() throws GitReplicaException{
		String searchUrl = "git@github.com:umamaheswaraa/App.git";
		Map<String, Integer> map = contentService.getReposotoryContent(searchUrl,"imaginian");
		Assert.assertNotNull(map);
		Assert.assertFalse(map.size()>1);
	}
	
	@Test(expected=GitReplicaException.class)
	public void getContentwrongProjectName() throws GitReplicaException{
		String searchUrl = "git@github.com:umamaheswaraa/CRUD-App.git";
		Map<String, Integer> map = contentService.getReposotoryContent(searchUrl,"imaginian");
		Assert.assertNotNull(map);
		Assert.assertFalse(map.size()>1);
	}
	@Test 
	public void getContentWithNullProjectName() throws GitReplicaException{
		String searchUrl = "git@github.com:umamaheswaraa/CRUD-App.git";
		Map<String, Integer> map = contentService.getReposotoryContent(searchUrl,null);
		Assert.assertNotNull(map);
		Assert.assertTrue(map.size()>1);
	}
	@Test(expected=GitReplicaException.class)
	public void getContentWithNullUrl() throws GitReplicaException{
		Map<String, Integer> map = contentService.getReposotoryContent(null,"imaginian");
		Assert.assertNotNull(map);
		Assert.assertFalse(map.size()>1);
	}
	@Test(expected=GitReplicaException.class)
	public void getContentWithNullUrlAndProjectName() throws GitReplicaException{
		Map<String, Integer> map = contentService.getReposotoryContent(null,null);
		Assert.assertNotNull(map);
		Assert.assertTrue(map.size()>1);
	}
	@Test(expected=GitReplicaException.class)
	public void getContentForAllNulls() throws GitReplicaException{
		Map<String, Integer> map = contentService.getReposotoryContent(null,null);
		Assert.assertNotNull(map);
		Assert.assertTrue(map.size()>1);
	}
	@Test
	public void getProjectNameWithNull() throws GitReplicaException{
		String projectName = contentService.getProjectName(null);
		Assert.assertNull(projectName);
	}
	@Test
	public void getProjectName() throws GitReplicaException{
			String projectName = contentService.getProjectName("git@github.com:umamaheswaraa/CRUD-App.git");
			Assert.assertEquals(projectName, "CRUD-App");
	}
	@Test
	public void getProjectNameWithNoDot() throws GitReplicaException{
			String projectName = contentService.getProjectName("git@github.com:umamaheswaraa/CRUD-App");
			Assert.assertNull(projectName);
	}
	
	@Test
	public void getSubFolderDetails() throws GitReplicaException{
		String path = "/users/umamaheswaraa/git/CRUD-App/";
		Map<String,Integer> map = contentService.getSubFolderDetails(path,"src/main/resources/");
			Assert.assertNotNull(map);
			Assert.assertTrue(map.size()==0);
	}
	
	@Test(expected=GitReplicaException.class)
	public void getSubFolderDetailsNullSubPath() throws GitReplicaException{
		String path = "/users/umamaheswaraa/git/CRUD-App/";
		contentService.getSubFolderDetails(path,null);
	}
	@Test
	public void getSubFolderDetailsNullPath() throws GitReplicaException{
		Map<String,Integer> map = contentService.getSubFolderDetails(null,"src/main/resources/");
		Assert.assertNotNull(map);
			
	}
	@Test
	public void TestgetSubPathWithNull() throws GitReplicaException{
		String str = contentService.getSubPath(null);
		Assert.assertNotNull(str);
		Assert.assertTrue(str.length()==0);
	}
	@Test
	public void TestgetSubPath() throws GitReplicaException{
		String prepath="src/";
		String str = contentService.getSubPath(prepath);
		
		Assert.assertNotNull(str);
		Assert.assertEquals(str, "");
	}
	
	@Test
	public void getCommitBytes() throws GitReplicaException{
		String path = "git@github.com:umamaheswaraa/CRUD-App.git";
		String subpath="pom.xml";
		String str = contentService.getStringContent(path, subpath);
		Assert.assertNotNull(str);
	}
	
	@Test(expected=GitReplicaException.class)
	public void getCommitBytesWithNullPath() throws GitReplicaException{
		String subpath="pom.xml";
		String str = contentService.getStringContent(null, subpath);
		Assert.assertNotNull(str);
	}
	
	@Test(expected=GitReplicaException.class)
	public void getCommitBytesnullSubPath() throws GitReplicaException{
		String path = "git@github.com:umamaheswaraa/CRUD-App.git";
		String str = contentService.getStringContent(path, null);
		Assert.assertNotNull(str);
	}
	@Test
	public void getCommitBytesValues() throws GitReplicaException{
		String path = "git@github.com:umamaheswaraa/CRUD-App.git";
		String subpath="pom.xml";
		String str = contentService.getStringContent(path, subpath);
		Assert.assertNotNull(str);
	}
	@Test
	public void getCommitBytesForJavaFile() throws GitReplicaException{
		String path = "git@github.com:umamaheswaraa/CRUD-App.git";
		String subpath="src/main/java/com/imaginea/crud/controller/EmployeeController.java";
		String str = contentService.getStringContent(path, subpath);
			Assert.assertNotNull(str);
	}
	@Test
	public void getRepositoryConfigDetails() throws GitReplicaException{
		String path = "git@github.com:umamaheswaraa/CRUD-App.git";
		
			String name =contentService.fetchUserInfo(path);
			Assert.assertNotNull(name);
	}
	@Test
	public void getRepositoryConfigWithNulla() throws GitReplicaException{
			String name =contentService.fetchUserInfo(null);
			Assert.assertNotNull(name);
	}
	@Test
	public void getRepositoryConfigDetail() throws GitReplicaException{
		String path = "git@github.com:umamaheswaraa/CRUD-App";
		
			String name =contentService.fetchUserInfo(path);
			Assert.assertNotNull(name);
	}
	
	@Test
	public void getListOfCommits() throws GitReplicaException{
		String path = "git@github.com:umamaheswaraa/CRUD-App.git";
		
		Map<String, List<String>> map =	contentService.getListOfCommits(path);
		Assert.assertNotNull(map);
		Assert.assertTrue(map.size()>1);
	}
	@Test(expected=GitReplicaException.class)
	public void getListOfCommitsWithNullPath() throws GitReplicaException{
		
		Map<String, List<String>> map =	contentService.getListOfCommits(null);
		Assert.assertNotNull(map);
		Assert.assertTrue(map.size()>1);
	}
	@Test(expected=GitReplicaException.class)
	public void getListOfCommitsWithNullP() throws GitReplicaException{
		
		Map<String, List<String>> map =	contentService.getListOfCommits(null);
		Assert.assertNotNull(map);
		Assert.assertTrue(map.size()>1);
	}
	@Test
	public void getListOfCommitsAllValues() throws GitReplicaException{
		String path = "git@github.com:umamaheswaraa/CRUD-App.git";		
		Map<String, List<String>> map =	contentService.getListOfCommits(path);
		Assert.assertNotNull(map);
		Assert.assertTrue(map.size()>1);
	}
	@Test
	public void getListOfRemotes() throws GitReplicaException{
		String path = "https://github.com/centic9/jgit-cookbook.git";
		List<String> list = contentService.getListOfRemotes(path);
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size()>1);
	}
	
	@Test
	public void getListOfRemotesnullPath() throws GitReplicaException{
		List<String> list = contentService.getListOfRemotes(null);
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size()==0);
	}
	@Test
	public void getListOfRemotesValues() throws GitReplicaException{
		String path = "https://hub.com/centic9/jgit-cookbook";
		List<String> list =	contentService.getListOfRemotes(path);
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size()==0);
	}
	@Test
	public void getListOfTags() throws GitReplicaException{
		String path = "https://github.com/centic9/jgit-cookbook.git";
		List<String> list = contentService.getListOfTags(path);
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size()>1);
	}
	@Test
	public void getListOfTagsWithNullPath() throws GitReplicaException{
		String path = "https://github.com/centic9/jgit-cookbook.git";
		List<String> list = contentService.getListOfTags(path);
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size()>1);
	}
	@Test
	public void getListOfTagsWithNulls() throws GitReplicaException{
		List<String> list = contentService.getListOfTags(null);
		Assert.assertNotNull(list);
		Assert.assertFalse(list.size()>1);
	}
	@Test
	public void getListOfTagsWithValues() throws GitReplicaException{
		String path = "thub.com/centic9/jgit-cookbook";
		List<String> list = contentService.getListOfTags(path);
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size()==0);
	}
	
	@Test
	public void getListOfContributors() throws GitReplicaException{
		String path = "https://github.com/centic9/jgit-cookbook.git";
		List<String> list = contentService.getListOfContributors(path);
		Assert.assertNotNull(list);
		Assert.assertFalse(list.size()>1);
	}
	
	@Test(expected=GitReplicaException.class)
	public void getListOfContributorsWithNullPath() throws GitReplicaException{
		List<String> list = contentService.getListOfContributors(null);
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size()>1);
	}
	
	@Test(expected=GitReplicaException.class)
	public void getListOfContributorsWithNullP() throws GitReplicaException{
		List<String> list = contentService.getListOfContributors(null);
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size()>1);
	}
	
	@Test(expected=GitReplicaException.class)
	public void getListOfContributorsValues() throws GitReplicaException{
		String path = "https://github.com/centic9/cooook";
		List<String> list = contentService.getListOfContributors(path);
		Assert.assertNotNull(list);
		Assert.assertFalse(list.size()>1);
	}
	
	
	
}
