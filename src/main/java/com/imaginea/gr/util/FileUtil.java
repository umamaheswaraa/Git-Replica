package com.imaginea.gr.util;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.imaginea.gr.exception.GitReplicaException;
import com.imaginea.gr.service.impl.ContentServiceImpl;

public class FileUtil {

	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
	
	public File checkAndCreateDirectory(String userName, String projectName)throws GitReplicaException {
		File tmpDir = null;
		try {
			if (null == userName) {
				tmpDir = new File(
						System.getProperty(Constants.JAVA_IO_TEMPDIR),
						Constants.TMP_FOLDER + projectName);
				tmpDir.mkdirs();
			} else {
				tmpDir = new File(
						System.getProperty(Constants.JAVA_IO_TEMPDIR),
						Constants.TMP_FOLDER + userName + Constants.git_FOLDER
								+ projectName);
				tmpDir.mkdirs();
			}
		} catch (Exception e) {
			logger.error(" Exception when creating the directory :"+e.getMessage());
			throw new GitReplicaException("Exception when creating the directory :"+e.getMessage());
		}

		return tmpDir;
	}
}
