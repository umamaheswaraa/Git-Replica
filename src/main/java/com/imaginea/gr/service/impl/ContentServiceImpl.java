package com.imaginea.gr.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TimeZone;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryCache;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.eclipse.jgit.util.FS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.imaginea.gr.exception.GitReplicaException;
import com.imaginea.gr.service.ContentService;
import com.imaginea.gr.util.Constants;
import com.imaginea.gr.util.FileUtil;

/**
 * @author umamaheswaraa
 *
 */
public class ContentServiceImpl implements ContentService {

	private static final Logger logger = LoggerFactory.getLogger(ContentServiceImpl.class);
	
	FileUtil fileUtil;
	
	public FileUtil getFileUtil() {
		return fileUtil;
	}

	public void setFileUtil(FileUtil fileUtil) {
		this.fileUtil = fileUtil;
	}

	private File cloneRepo(String url,String userName,String projectName) throws GitReplicaException{
		File tmpDir=null;
		Repository repo = null;
		try{
			logger.info("Before Cloning Url: "+url+" projectName:"+projectName+" userName: "+userName);
			tmpDir = fileUtil.checkAndCreateDirectory(userName, projectName);
			try {
				logger.info("File path: "+tmpDir.getPath());
				
				if (RepositoryCache.FileKey.isGitRepository(new File(tmpDir.getPath()), FS.DETECTED)) {
						logger.info("Already Repository present :"+tmpDir.getPath());
				} else {
					Git git = Git.open(tmpDir);
					repo = git.getRepository();
					if(!hasAtLeastOneReference(repo)){
						Git.cloneRepository().setURI(url).setDirectory(tmpDir).call();
					} 
				}
				
				
			} catch (InvalidRemoteException e) {
				logger.error("Exception InvalidRemoteException :"+e.getMessage());
				throw new GitReplicaException("Exception while Cloning the Repository :"+e.getMessage());
			} catch (TransportException e) {
				logger.error("Exception InvalidRemoteException :"+e.getMessage());
				throw new GitReplicaException("Exception while Cloning the Repository :"+e.getMessage());
			} catch (GitAPIException e) {
				logger.error("Exception GitAPIException :"+e.getMessage());
				throw new GitReplicaException("Exception while Cloning the Repository :"+e.getMessage());
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception when clonig the repo :"+e.getMessage());
			throw new GitReplicaException("Exception while Cloning the Repository :"+e.getMessage());
		}
		return tmpDir;
	}
	
	private boolean hasAtLeastOneReference(Repository repo) {
	    for (Ref ref : repo.getAllRefs().values()) {
	      if (ref.getObjectId() == null)
	        continue;
	      return true;
	    }
	    return false;
	  }

	
	public Map<String, Integer> getReposotoryContent(String url,String userName,String projectName)throws GitReplicaException{
		Map<String, Integer> retMap = new HashMap<String, Integer>();
		logger.info("Inside getReposotoryContent method");
		Repository repo=null;
		try {
			if(projectName==null || (projectName!=null && projectName.length()==0))
			{
				projectName = getProjectName(url);
			}
			File gitDir = this.cloneRepo(url, userName, projectName);
			logger.info("gitDir :"+gitDir);
			Git git = Git.open(gitDir);
			repo = git.getRepository();
			
			ObjectId head = repo.resolve(Constants.HEAD);
			RevWalk  walk = new RevWalk(repo);
			DirCache cache = new DirCache(gitDir, FS.DETECTED);
			
			TreeWalk treeWalk = new TreeWalk(repo);
			treeWalk.addTree(walk.parseTree(head));
			treeWalk.addTree(new DirCacheIterator(cache));
			while (treeWalk.next())
			{
			    ObjectLoader loader = repo.open(treeWalk.getObjectId(0));
			    
			    retMap.put(treeWalk.getNameString(),loader.getType());
			}
		
		} catch (Exception e) {
			logger.error("Exception when getting repo content :"+e.getMessage());
			throw new GitReplicaException("Exception while fetching repo content: "+e.getMessage());
		}finally{
			if(repo!=null){
				repo.close();
			}
		}
		return retMap;
	}
	
	public String getProjectName(String searchUrl) throws GitReplicaException{
		String projectName=null;
		try{			
			if(searchUrl!=null && searchUrl.contains("."+Constants.GIT)){
				StringTokenizer st = new StringTokenizer(searchUrl, "/");
				while(st.hasMoreTokens()){
					String token = st.nextToken();
					if(token.contains("."))
					{
						projectName = token.substring(0, token.indexOf("."));
					}
				}
				logger.info("projectName :"+projectName);
			}
		}catch (Exception e) {
			throw new GitReplicaException("Exception when getting project Name from URL"+e.getMessage());
		}
		return projectName;
	}
	
	private Repository getRepository(String path,String userName)throws GitReplicaException{
		Repository repo=null;
		try{
			String projectName = this.getProjectName(path);
			File gitDir = fileUtil.checkAndCreateDirectory(userName, projectName);
			logger.info("getRepository -- gitDir :"+gitDir.getPath());
			Git git = Git.open(gitDir);
			repo = git.getRepository();
		}catch (Exception e) {
			logger.error("Exception when getting existing repository :"+e.getMessage());
			throw new GitReplicaException("Exception when getting existing repository"+e.getMessage());
		}
		return repo;
	}
	
	private  int getFileModeForInt(String path) {
        if(path.contains(".")) {
                return 3;
        } else {
                return 2;
        }
	}
	
	
	public Map<String, Integer> getSubFolderDetails(String path, String subPath,String userName) throws GitReplicaException
	{
		int length=subPath.length();
		Map<String,Integer> map = new HashMap<String, Integer>();
		try{
			List<String> list = this.browseTree(path, subPath,userName);
			
			for(String entry :list){
				String subString = entry.substring(length);
				String[] strs = StringUtils.delimitedListToStringArray(subString, "/");
				int value = getFileModeForInt(strs[0]);		
				map.put(strs[0],value );
			}
			
		}catch (Exception e) {
			logger.error("Excpetion when getting subfolder info :"+e.getMessage());
			throw new GitReplicaException("Exception when getting Subfolder info from repository"+e.getMessage());
		}
		return map;
	}
	
	public String getSubPath(String prePath) throws GitReplicaException
	{
		String retStr="";
		try{
			String[] strs = StringUtils.delimitedListToStringArray(prePath, "/");
			int length = strs.length;
			logger.info("length :"+length);
			for(int i=0;i<(length-2);i++){
					retStr = retStr + strs[i]+"/";
			}
			
		}catch (Exception e) {
			logger.error("Exception when getting subpath :"+e.getMessage());
			throw new GitReplicaException("Exception when getting Sub path "+e.getMessage()); 
		}
		return retStr;
	}
	
	public String fetchUserInfo(String path, String userName)throws GitReplicaException{
		Repository repo=null;
		String name=null;
		try{
			 repo = this.getRepository(path, userName);
			 Config config = repo.getConfig();
             name = config.getString("user", null, "name");
             String email = config.getString("user", null, "email");
             if (name == null || email == null) {
                     logger.info("User identity is unknown!");
             } else {
            	 logger.info("User identity is " + name + " <" + email + ">");
             }
             
		}catch (Exception e) {
			logger.error("Exception when fetching user info "+e.getMessage());
			throw new GitReplicaException("Exception when fetching user info "+e.getMessage());
		}finally{
			if(repo!=null){
				repo.close();
			}
		}
		return name;
	}
	
	
	public List<String> browseTree(String path,String subPath,String userName) throws Exception{
		List<String> list = new ArrayList<String>();
		Repository repo=null;
		try{
			 repo = this.getRepository(path,userName);
			
			//DirCache contains all files of the repository
            DirCache index = DirCache.read(repo);
            DirCacheEntry[] entry1 =  index.getEntriesWithin(subPath);
            
            //Getting only list of folders under the subPath
            for(int i = 0; i < entry1.length;i++) {
                DirCacheEntry entry = entry1[i];
                if(entry!=null)
                {
                	list.add(entry.getPathString());
                }
            }
			
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("Excpetion when browse Tree :"+e.getMessage());
			throw new GitReplicaException("Exception when getting browseTree from repository"+e.getMessage());
		}finally{
			if(repo !=null){
				repo.close();
			}
		}
		return list;
	}
	
	public String getStringContent(String path, String subpath,String userName) throws GitReplicaException{
		Repository repository=null;
		String content=null;
		try{
			repository = this.getRepository(path, null);

            ObjectId revId = repository.resolve(Constants.HEAD);
            TreeWalk treeWalk = new TreeWalk(repository);
            treeWalk.setRecursive(true);
            treeWalk.addTree(new RevWalk(repository).parseTree(revId));
            treeWalk.setFilter(PathFilter.create(subpath));

            while (treeWalk.next())
            {
                   ObjectLoader loader = repository.open(treeWalk.getObjectId(0));
                   byte[] bytes = loader.getBytes();
                   content = new String(bytes);
            }
            	
		}catch (Exception e) {
			logger.error("Exception while getting String content"+e.getMessage());
			throw new GitReplicaException("Exception while getting String Content :"+e.getMessage());
		}finally{
			repository.close();			
		}
		return content;
	}
	
	public Map<String, List<String>> getListOfCommits(String path, String userName)throws GitReplicaException{
		long time;
		String strDate=null;
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> list = null;
		try{
			String projectName = this.getProjectName(path);
			File gitDir = fileUtil.checkAndCreateDirectory(userName, projectName);
			Git git = Git.open(gitDir);
			SimpleDateFormat sdf = new SimpleDateFormat("MMMM d,yyyy", Locale.ENGLISH);
			sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
			
			Iterable<RevCommit> log = git.log().call();
		    for (Iterator<RevCommit> iterator = log.iterator(); iterator.hasNext();) {
		        RevCommit rev = iterator.next();
		        time = (long)rev.getCommitTime()*1000;
		        strDate = sdf.format(time);
		        if(map.containsKey(strDate)){
		        	list = map.get(strDate);
		        	list.add(rev.getFullMessage());
		        }else{
		        	list = new ArrayList<String>();
		        	list.add(rev.getFullMessage());
		        	map.put(strDate,list);
		        }		        
		    }
		    
		}catch (Exception e) {
			logger.error("Exception while getting list of commits :"+e.getMessage());
			throw new GitReplicaException("Exception while getting list of commits :"+e.getMessage());
		}
		return map;
	}
	
	public List<String> getListOfRemotes(String path, String userName) throws GitReplicaException{
		Repository repository=null;
		List<String> list= new ArrayList<String>();
		try{
			Repository repo = this.getRepository(path, userName);
			
			Map<String, Ref> ref = repo.getAllRefs();
			Set<Entry<String, Ref>> entry = ref.entrySet();
			for(Entry<String, Ref> refentry : entry){
				
				if(refentry.getKey().contains(Constants.ORGIN)){
					logger.info("Key "+refentry.getKey());
					list.add(refentry.getKey());	
				}
			}
			list = getRemoteList(list);
			
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception when getting list of remotes :"+e.getMessage());
			throw new GitReplicaException("Exception when getting list of remotes :"+e.getMessage());
		}finally{
			if(repository!=null){
				repository.close();
			}
		}
		return list;
	}
	
	public List<String> getRemoteList(List<String> list)throws GitReplicaException{
		List<String> retList=new ArrayList<String>();
		try{
			if(list!=null){
				for(String str: list){
					retList.add(str.substring(Constants.ORGIN.length()));					
				}
			}
		}catch (Exception e) {
			logger.error("Exception when getting RemoteList :"+e.getMessage());
			throw new GitReplicaException("Exception when getting RemoteList :"+e.getMessage());
		}
		return retList;
	}
	
	public List<String> getListOfTags(String path, String userName)throws GitReplicaException{
		List<String> retList=new ArrayList<String>();
		try{
			Repository repo = this.getRepository(path, userName);
			
			Map<String, Ref> ref = repo.getTags();
			Set<Entry<String, Ref>> entry = ref.entrySet();
			for(Entry<String, Ref> refentry : entry){
					logger.info("Key "+refentry.getKey());
					retList.add(refentry.getKey());	
			}
		}catch (Exception e) {
			logger.error("Exception when getting list of tags :"+e.getMessage());
			throw new GitReplicaException("Exception when getting list of tags :"+e.getMessage());
		}
		return retList;
	}
	
	public List<String> getListOfContributors(String path, String userName)throws GitReplicaException{
		List<String> retList=new ArrayList<String>();
		try{
			String projectName = this.getProjectName(path);
			File gitDir = fileUtil.checkAndCreateDirectory(userName, projectName);
			Git git = Git.open(gitDir);
			Repository repo = git.getRepository();
			SimpleDateFormat sdf = new SimpleDateFormat("MMMM d,yyyy", Locale.ENGLISH);
			sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
			
			Iterable<RevCommit> log = git.log().all().call();
		    for (Iterator<RevCommit> iterator = log.iterator(); iterator.hasNext();) {
		        RevCommit rev = iterator.next();
		        logger.info("rev auth email: "+rev.getAuthorIdent().getEmailAddress());
		        logger.info("rev auth name: "+rev.getAuthorIdent().getName());
		        logger.info("rev auth when: "+rev.getAuthorIdent().getWhen());
		        logger.info("rev auth offcet: "+rev.getAuthorIdent().getTimeZoneOffset());
		        logger.info("rev time : "+rev.getCommitTime());
		        logger.info("rev  name: "+rev.name());
		    }
			
		}catch (Exception e) {
			logger.error("Exception when getting list of contributors :"+e.getMessage());
			throw new GitReplicaException("Exception when getting list of contributors :"+e.getMessage());
		}
		return retList;
	}
	
}
