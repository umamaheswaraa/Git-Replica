package com.imaginea.gr.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/applicationContext.xml"})
public class FileUtilTest {

	@Autowired
	FileUtil fileUtil; 
	
	@Test
	public void checkAndCreateDirectoryWithNullUser(){
		try{
			fileUtil.checkAndCreateDirectory(null, "B2C");
		}catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	@Test
	public void checkAndCreateDirectoryWIthUser(){
		try{
			fileUtil.checkAndCreateDirectory("akasam", "B2C");
		}catch (Exception e) {
			// TODO: handle exception
		}
		
	}
}
