package com.imaginea.gr.service;

import java.util.List;
import java.util.Map;

import com.imaginea.gr.exception.GitReplicaException;

/**
 * @author umamaheswaraa
 *
 */
public interface ContentService {
	public Map<String, Integer> getReposotoryContent(String url,String projectName)throws GitReplicaException;
	public Map<String, Integer> getSubFolderDetails(String path, String subPath)throws GitReplicaException;
	public String getSubPath(String prePath) throws GitReplicaException;
	public String getStringContent(String path, String subpath)throws GitReplicaException;
	public String fetchUserInfo(String path)throws GitReplicaException;
	public String getProjectName(String searchUrl)throws GitReplicaException;
	public Map<String, List<String>> getListOfCommits(String path)throws GitReplicaException;
	public List<String> getListOfRemotes(String path)throws GitReplicaException;
	public List<String> getListOfTags(String path)throws GitReplicaException;
	public List<String> getListOfContributors(String path)throws GitReplicaException;
}
