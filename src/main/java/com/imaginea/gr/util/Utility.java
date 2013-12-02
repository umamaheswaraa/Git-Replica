package com.imaginea.gr.util;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.imaginea.gr.exception.GitReplicaException;

/**
 * @author umamaheswaraa
 *
 */
public class Utility {
    
    private static Logger logger = LoggerFactory.getLogger(Utility.class);
    
    /**
     * This method will create a directory if not available
     * @param userName
     * @param projectName
     * @return File
      * @throws GitReplicaException
     */
    public File checkAndCreateDirectory(String projectName)throws GitReplicaException {
        File tmpDir = null;
        try {
                tmpDir = new File(
                        System.getProperty(Constants.JAVA_IO_TEMPDIR),
                        Constants.TMP_FOLDER + projectName);
                tmpDir.mkdirs();
            
        } catch (Exception e) {
            logger.error(" Exception when creating the directory :"+e.getMessage());
            throw new GitReplicaException("Exception from method checkAndCreateDirectory ",e);
        }

        return tmpDir;
    }
    
    /**
     * This method will get the project name for the specified URL
     * @param searchUrl
     * @return String
      * @throws GitReplicaException
     */
    public String getProjectName(String searchUrl) throws GitReplicaException{
        String projectName=null;
        try{        
            if(searchUrl!=null && searchUrl.contains("."+Constants.GIT)){
                StringTokenizer st = new StringTokenizer(searchUrl, "/");
                while(st.hasMoreTokens()){
                    String token = st.nextToken();
                    if(token.contains("."))
                    {
                        projectName = token.substring(0, token.indexOf('.'));
                    }
                }
                logger.info("projectName :"+projectName);
            }
        }catch (Exception e) {
            logger.info("Exception occured in getProjectName method "+e.getMessage());
            throw new GitReplicaException("Exception from method getProjectName ",e);
        }
        return projectName;
    }
    
    public static Date getCurrentTime(){
        Calendar today = Calendar.getInstance();
        return today.getTime();
    }
}
