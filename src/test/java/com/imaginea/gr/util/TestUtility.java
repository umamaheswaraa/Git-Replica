package com.imaginea.gr.util;

import java.io.File;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.imaginea.gr.exception.GitReplicaException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/applicationContext.xml"})
public class TestUtility {

	@Autowired
	Utility utility;
	
	@Test
	public void checkAndCreateDirectory() throws GitReplicaException{
	
		File tmpDir = utility.checkAndCreateDirectory("B2C");
		Assert.assertNotNull(tmpDir);
	}
	
	@Test
	public void checkAndCreateDirectoryWIthUser() throws GitReplicaException{
		File tmpDir =	utility.checkAndCreateDirectory("B2C1");
		Assert.assertNotNull(tmpDir); 
		
	}
	@Test
	public void getProjectNameTest() throws GitReplicaException{
		String searchUrl = "git@github.com:umamaheswaraa/CRUD-App.git";
		String name =	utility.getProjectName(searchUrl);
		Assert.assertNotNull(name); 
		Assert.assertEquals(name, "CRUD-App");
	}
	@Test
	public void getProjectNameNullValueTest() throws GitReplicaException{
		String name =	utility.getProjectName(null);
		Assert.assertNull(name); 
	}
	@Test
	public void getProjectName() throws GitReplicaException{
		String searchUrl = "git@github.com:umamaheswaraa/CRUD-App";
		String name =	utility.getProjectName(searchUrl);
		Assert.assertNull(name); 
	}
	
}
