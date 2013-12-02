package com.imaginea.gr.service;

import java.util.List;
import java.util.Map;

import com.imaginea.gr.exception.GitReplicaException;

/**
 * @author umamaheswaraa
 *
 */
public interface ContentService {
    Map<String, Integer> getReposotoryContent(String url,String projectName)throws GitReplicaException;
    Map<String, Integer> getSubFolderDetails(String path, String subPath)throws GitReplicaException;
    String getSubPath(String prePath) throws GitReplicaException;
    String getStringContent(String path, String subpath)throws GitReplicaException;
    String fetchUserInfo(String path)throws GitReplicaException;
    String getProjectName(String searchUrl)throws GitReplicaException;
    Map<String, List<String>> getListOfCommits(String path)throws GitReplicaException;
    List<String> getListOfRemotes(String path)throws GitReplicaException;
    List<String> getListOfTags(String path)throws GitReplicaException;
    List<String> getListOfContributors(String path)throws GitReplicaException;
}
