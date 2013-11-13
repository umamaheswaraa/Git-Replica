package com.imaginea.gr.service;

import java.util.List;
import java.util.Map;

public interface ContentService {
	public void getRemoteRepository();
	public Map<String, Integer> getReposotoryContent(String url,String userName);
	public void getFolderContent(String path);
	public byte[] getByteContent(String path, String subpath );
	public void getFileAttributes(String path);
	public Map<String, Integer> getSubFolderDetails(String path, String subPath,String userName);
	public String getSubPath(String prePath);
	public byte[] getBlobContent(String path, String subpath );
	public String getStringContent(String path, String subpath, String userName);
}
