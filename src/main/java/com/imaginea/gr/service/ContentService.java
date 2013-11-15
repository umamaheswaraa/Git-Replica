package com.imaginea.gr.service;

import java.util.List;
import java.util.Map;

import com.imaginea.gr.exception.GitReplicaException;

/**
 * @author umamaheswaraa
 *
 */
public interface ContentService {
	public Map<String, Integer> getReposotoryContent(String url,String userName,String projectName)throws GitReplicaException;
	public Map<String, Integer> getSubFolderDetails(String path, String subPath,String userName)throws GitReplicaException;
	public String getSubPath(String prePath) throws GitReplicaException;
	public String getStringContent(String path, String subpath, String userName)throws GitReplicaException;
	public String fetchUserInfo(String path, String userName)throws GitReplicaException;
	public String getProjectName(String searchUrl)throws GitReplicaException;
	public Map<String, List<String>> getListOfCommits(String path, String userName)throws GitReplicaException;
	public List<String> getListOfRemotes(String path, String userName)throws GitReplicaException;
	public List<String> getListOfTags(String path, String userName)throws GitReplicaException;
	public List<String> getListOfContributors(String path, String userName)throws GitReplicaException;
}
