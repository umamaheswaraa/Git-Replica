package com.imaginea.gr.service;

import java.util.Map;

public interface ContentService {
	public void getRemoteRepository();
	public String getReposotoryContent(String url,String userName);
	public void getFolderContent(String path);

}
