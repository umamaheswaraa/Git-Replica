package com.imaginea.gr.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand.ListMode;
import org.eclipse.jgit.api.LsRemoteCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.util.FS;
import org.springframework.beans.factory.annotation.Autowired;

import com.imaginea.gr.service.ContentService;
import com.imaginea.gr.util.Constants;
import com.imaginea.gr.util.FileUtil;
import com.imaginea.gr.util.RestClient;

public class ContentServiceImpl implements ContentService {

	FileUtil fileUtil;
	
	public FileUtil getFileUtil() {
		return fileUtil;
	}

	public void setFileUtil(FileUtil fileUtil) {
		this.fileUtil = fileUtil;
	}

	private File cloneRepo(String url,String userName,String projectName){
		File tmpDir=null;
		try{
			tmpDir = fileUtil.checkAndCreateDirectory(userName, projectName);
			try {
				Git.cloneRepository().setURI(url).setDirectory(tmpDir).call();
				
			} catch (InvalidRemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TransportException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (GitAPIException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return tmpDir;
	}
	
	public void getRemoteRepository(){
		Git git;
		try {
			git = new Git(new FileRepository(new File("/users/umamaheswaraa/git/CRUD-App")));
			
			Repository repo = git.getRepository();
			String head = repo.getFullBranch();
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String getReposotoryContent(String url,String userName){
		String retStr = null;
		Map<String, Integer> retMap = new HashMap<String, Integer>();
		try {
			String projectName = getProjectName(url);
			System.out.println("Project Name"+projectName);
			System.out.println("url Name"+url);
			System.out.println("User Name"+userName);
			File gitDir = this.cloneRepo(url, userName, projectName);
			//File gitDir = new File("/users/umamaheswaraa/git/CRUD-App");
			System.out.println("gitDir :"+gitDir.getPath());
			Git git = Git.open(gitDir);
			Repository repo= git.getRepository();
			
			ObjectId head = repo.resolve("HEAD");
			Ref ref = repo.getRef("refs/heads/master");
			RevWalk  walk = new RevWalk(repo);
			DirCache cache = new DirCache(gitDir, FS.DETECTED);
			
			TreeWalk treeWalk = new TreeWalk(repo);
			treeWalk.addTree(walk.parseTree(head));
			treeWalk.addTree(new DirCacheIterator(cache));
	
			while (treeWalk.next())
			{
			    System.out.println("---------------------------");
			    System.out.append("name: ").println(treeWalk.getNameString());
			    System.out.append("path: ").println(treeWalk.getPathString());
			    
			    ObjectLoader loader = repo.open(treeWalk.getObjectId(0));

			    retMap.put(treeWalk.getNameString(),loader.getType());
			   System.out.append("directory: ").println(loader.getType()==2);
			 }
			retStr =  ConvertMaptoJsonString(retMap); 
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retStr;
	}
	
	private String ConvertMaptoJsonString(Map<String, Integer> map)
	{
		ObjectMapper mapper = new ObjectMapper();
		String retVal=null;
		try {
			retVal = mapper.writeValueAsString(map);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retVal;
	}
	public void getFolderContent(String path)
	{
		File gitDir = new File(path);
		try{
			System.out.println("gitDir :"+gitDir.getPath());
			Git git = Git.open(gitDir);
			Repository repo= git.getRepository();

			ObjectId head = repo.resolve("HEAD");
			Ref ref = repo.getRef("refs/heads/master");
			RevWalk  walk = new RevWalk(repo);
			DirCache cache = new DirCache(gitDir, FS.DETECTED);
			
			TreeWalk treeWalk = new TreeWalk(repo);
			treeWalk.addTree(walk.parseTree(head));
			treeWalk.addTree(new DirCacheIterator(cache));

			  while (treeWalk.next())
			  {
			    System.out.println("---------------------------");
			    System.out.append("name: ").println(treeWalk.getNameString());
			    System.out.append("path: ").println(treeWalk.getPathString());

			    ObjectLoader loader = repo.open(treeWalk.getObjectId(0));
			 }

		}catch (Exception e) {
			// TODO: handle exception
		}
		        
	}

	private String getProjectName(String searchUrl){
		String projectName=null;
		try{			
			if(searchUrl!=null && searchUrl.contains("."+Constants.GIT)){
				StringTokenizer st = new StringTokenizer(searchUrl, "/");
				while(st.hasMoreTokens()){
					String token = st.nextToken();
					System.out.println("token :"+ token);
					if(token.contains("."))
					{
						projectName = token.substring(0, token.indexOf("."));
					}
				}
				System.out.println("projectName :"+projectName);
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		return projectName;
	}
	
	
}
