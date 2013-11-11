package com.imaginea.gr.util;

import java.io.File;

public class FileUtil {

	public File checkAndCreateDirectory(String userName,String projectName)
	{
		File tmpDir=null;
		try{
			if(null==userName){
				tmpDir = new File(System.getProperty(Constants.JAVA_IO_TEMPDIR), "tmp/"
						+ projectName);
				tmpDir.mkdirs();				
			}else{
				tmpDir = 	new File(System.getProperty(Constants.JAVA_IO_TEMPDIR),"tmp/"+userName+"/git/"+projectName);
				tmpDir.mkdirs();
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		return tmpDir;
	}
}
