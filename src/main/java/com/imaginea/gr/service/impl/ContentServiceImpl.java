package com.imaginea.gr.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TimeZone;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revplot.PlotCommitList;
import org.eclipse.jgit.revplot.PlotLane;
import org.eclipse.jgit.revplot.PlotWalk;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.eclipse.jgit.util.FS;
import org.hibernate.classic.Validatable;
import org.springframework.util.StringUtils;


import com.imaginea.gr.service.ContentService;
import com.imaginea.gr.util.Constants;
import com.imaginea.gr.util.FileUtil;
import com.imaginea.gr.util.UtilitiesTest;

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
	
	public Map<String, Integer> getReposotoryContent(String url,String userName,String projectName){
		Map<String, Integer> retMap = new HashMap<String, Integer>();
		try {
			if(projectName==null || (projectName!=null && projectName.length()==0))
			{
				projectName = getProjectName(url);
			}
			File gitDir = this.cloneRepo(url, userName, projectName);
			Git git = Git.open(gitDir);
			Repository repo= git.getRepository();
			
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retMap;
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

	public String getProjectName(String searchUrl){
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
	
	public byte[] getBlobContent(String path, String subpath ){
		byte[] content=null;
		
		try{
			Repository repo = this.getRepository(path,null);
			ObjectId lastCommitId  = repo.resolve(Constants.HEAD);
			//Ref ref = repo.getRef("refs/heads/master");
			//ObjectLoader loader = repo.open(ref.getObjectId());
			//String str= loader.toString();
			
			//loader.copyTo(System.out);
            System.out.println("Print contents of tree of head of master branch, i.e. the latest binary tree information");

			RevWalk  walk = new RevWalk(repo);
			RevCommit commit = walk.parseCommit(lastCommitId );
			walk.markStart(commit);
			
			RevTree tree = commit.getTree();
			System.out.println("Found Tree: " + tree);
			TreeWalk treeWalk = new TreeWalk(repo);
			treeWalk.addTree(tree);
			treeWalk.setRecursive(true);
			treeWalk.setFilter(PathFilter.create(subpath));
			
			 ObjectId objectId = treeWalk.getObjectId(0);
		 
             ObjectLoader loader = repo.open(objectId);

             // and then one can the loader to read the file
             loader.copyTo(System.out);

		}catch (Exception e) {
			// TODO: handle exception
		}
		
		return content;
	}
	
	public byte[] getByteContent(String path, String subpath ){
		byte[] content=null;
		
		try{
			Repository repo = this.getRepository(path,null);
			//ObjectId head = repo.resolve("HEAD");
			Ref ref = repo.getRef("refs/heads/master");
			ObjectLoader loader = repo.open(ref.getObjectId());
			String str= loader.toString();
			
			loader.copyTo(System.out);
            System.out.println("Print contents of tree of head of master branch, i.e. the latest binary tree information");

			RevWalk  walk = new RevWalk(repo);
			RevCommit commit = walk.parseCommit(ref.getObjectId());
			RevTree tree = walk.parseTree(commit.getTree().getId());
			System.out.println("Found Tree: " + tree);
			//TreeWalk treeWalk = new TreeWalk(repo);
			loader = repo.open(tree.getId());
            loader.copyTo(System.out);

		}catch (Exception e) {
			// TODO: handle exception
		}
		
		return content;
	}
	
	public void getFileAttributes(String path){
		try{
			 // find the Tree for current HEAD
			Repository repository = getRepository(path,null);
            RevTree tree = getTree(repository);

            printFile(repository, tree);

            printDirectory(repository, tree);
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	private void printDirectory(Repository repository, RevTree tree){
		try{
			
			// look at directory, this has FileMode.TREE
			TreeWalk treeWalk = new TreeWalk(repository);
			treeWalk.addTree(tree);
			treeWalk.setRecursive(false);
			treeWalk.setFilter(PathFilter.create("src/main/java/com/imaginea/crud/controller/EmployeeController.java"));
			
			while(treeWalk.next()){
	        	   System.out.println("Depth :"+treeWalk.getDepth());
	        	   System.out.println("name :"+treeWalk.getNameString());
	        	   System.out.println("paTH :"+treeWalk.getPathString());
	        	   System.out.println("TREE COUNT :"+treeWalk.getTreeCount());
	        	   System.out.println("FILE MODE :"+treeWalk.getFileMode(0));
	        	   //ObjectLoader loader = treeWalk.getObjectId(0);
	        	   ObjectId objectId = treeWalk.getObjectId(0);
	                ObjectLoader loader = repository.open(objectId);
	                loader.copyTo(System.out);
	                
	           }	
			
			//  FileMode now indicates that this is a directory, i.e. FileMode.TREE.equals(fileMode) holds true
			FileMode fileMode = treeWalk.getFileMode(0);
			System.out.println("src: " + getFileMode(fileMode) + ", type: " + fileMode.getObjectType() + ", mode: " + fileMode);
						
		}catch (Exception e) {
			// TODO: handle exception
		}
	}

	
	private void printFile(Repository repository,RevTree tree){
		try{
			// now try to find a specific file
            TreeWalk treeWalk = new TreeWalk(repository);
            treeWalk.addTree(tree);
            treeWalk.setRecursive(true);
            treeWalk.setFilter(PathFilter.create("pom.xml"));
           while(treeWalk.next()){
        	   System.out.println("Depth :"+treeWalk.getDepth());
        	   System.out.println("name :"+treeWalk.getNameString());
        	   System.out.println("paTH :"+treeWalk.getPathString());
        	   System.out.println("TREE COUNT :"+treeWalk.getTreeCount());
        	   System.out.println("FILE MODE :"+treeWalk.getFileMode(0));
        	   ObjectId objectId = treeWalk.getObjectId(0);
               ObjectLoader loader = repository.open(objectId);
        	   
        	   
           }	
            
            // FileMode specifies the type of file, FileMode.REGULAR_FILE for normal file, FileMode.EXECUTABLE_FILE for executable bit set
            FileMode fileMode = treeWalk.getFileMode(0);
            ObjectLoader loader = repository.open(treeWalk.getObjectId(0));
            System.out.println("README.md: " + getFileMode(fileMode) + ", type: " + fileMode.getObjectType() + ", mode: " + fileMode + " size: " + loader.getSize());

		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	private RevTree getTree(Repository repository) {
		RevTree tree=null;
		try{
			ObjectId lastCommitId = repository.resolve(Constants.HEAD);
			
			// a RevWalk allows to walk over commits based on some filtering
			RevWalk revWalk = new RevWalk(repository);
			RevCommit commit = revWalk.parseCommit(lastCommitId);
			
			System.out.println("Time of commit (seconds since epoch): " + commit.getCommitTime());
			
			// and using commit's tree find the path
			tree = commit.getTree();
			System.out.println("Having tree: " + tree);
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		return tree;
	}
	
	private Repository getRepository(String path,String userName){
		Repository repo=null;
		try{
			String projectName = this.getProjectName(path);
			File gitDir = fileUtil.checkAndCreateDirectory(userName, projectName);
			System.out.println("gitDir :"+gitDir.getPath());
			Git git = Git.open(gitDir);
			repo = git.getRepository();
		}catch (Exception e) {
			// TODO: handle exception
		}
		return repo;
	}
	
	private  String getFileMode(FileMode fileMode) {
        if(fileMode.equals(FileMode.EXECUTABLE_FILE)) {
                return "Executable File";
        } else if(fileMode.equals(FileMode.REGULAR_FILE)) {
                return "Normal File";
        } else if(fileMode.equals(FileMode.TREE)) {
                return "Directory";
        } else if(fileMode.equals(FileMode.SYMLINK)) {
                return "Symlink";
        } else {
                // there are a few others, see FileMode javadoc for details
                throw new IllegalArgumentException("Unknown type of file encountered: " + fileMode);
        }
	}
	private  int getFileModeForInt(String path) {
        if(path.contains(".")) {
                return 3;
        } else {
                return 2;
        }
	}
	
	
	public Map<String, Integer> getSubFolderDetails(String path, String subPath,String userName)
	{
		int length=subPath.length();
		Map<String,Integer> map = new HashMap<String, Integer>();
		String retStr=null;
		try{
			List<String> list = this.browseTree(path, subPath,userName);
			
			for(String entry :list){
				String subString = entry.substring(length);
				System.out.println("subString :"+subString);
				String[] strs = StringUtils.delimitedListToStringArray(subString, "/");
				int value = getFileModeForInt(strs[0]);		
				map.put(strs[0],value );
			}
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		return map;
	}
	
	public String getSubPath(String prePath)
	{
		String retStr="";
		try{
			String[] strs = StringUtils.delimitedListToStringArray(prePath, "/");
			int length = strs.length;
			System.out.println("length :"+length);
			for(int i=0;i<(length-2);i++){
					retStr = retStr + strs[i]+"/";
			}
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		return retStr;
	}
	
	public String fetchUserInfo(String path, String userName){
		Repository repo=null;
		String name=null;
		try{
			 repo = this.getRepository(path, userName);
			 Config config = repo.getConfig();
             name = config.getString("user", null, "name");
             String email = config.getString("user", null, "email");
             if (name == null || email == null) {
                     System.out.println("User identity is unknown!");
             } else {
                     System.out.println("User identity is " + name + " <" + email + ">");
             }
             
		}catch (Exception e) {
			// TODO: handle exception
		}finally{
			if(repo!=null){
				repo.close();
			}
		}
		return name;
	}
	
	
	public List<String> browseTree(String path,String subPath,String userName){
		List<String> list = new ArrayList<String>();
		try{
			Repository repo = this.getRepository(path,userName);
			
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
			// TODO: handle exception
		}
		return list;
	}
	
	public String getStringContent(String path, String subpath,String userName){
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
                    System.out.println("---------------------------");
                    System.out.append("name: ").println(treeWalk.getNameString());
                    System.out.append("path: ").println(treeWalk.getPathString());

                    ObjectLoader loader = repository.open(treeWalk.getObjectId(0));
                    
                    /*System.out.append("directory: ").println(loader.getType()
                                    == org.eclipse.jgit.lib.Constants.OBJ_TREE);*/
                    //System.out.append("size: ").println(loader.getSize());
                    //loader.copyTo(System.out);
                    byte[] bytes = loader.getBytes();
                    content = new String(bytes);
                   //System.out.println("retString :"+retString);
            }
            	
		}catch (Exception e) {
			// TODO: handle exception
		}finally{
			repository.close();			
		}
		return content;
	}
	
	public Map<String, List<String>> getListOfCommits(String path, String userName){
		long time;
		String strDate=null;
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> list = null;
		try{
			String projectName = this.getProjectName(path);
			File gitDir = fileUtil.checkAndCreateDirectory(userName, projectName);
			Git git = Git.open(gitDir);
			Repository repo = git.getRepository();
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
			// TODO: handle exception
		}
		return map;
	}
	
	public List<String> getListOfRemotes(String path, String userName){
		Repository repository=null;
		List<String> list= new ArrayList<String>();
		try{
			Repository repo = this.getRepository(path, userName);
			
			Map<String, Ref> ref = repo.getAllRefs();
			Set<Entry<String, Ref>> entry = ref.entrySet();
			for(Entry<String, Ref> refentry : entry){
				
				if(refentry.getKey().contains(Constants.ORGIN)){
					System.out.println("Key "+refentry.getKey());
					list.add(refentry.getKey());	
				}
			}
			list = getRemoteList(list);
			
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}finally{
			if(repository!=null){
				repository.close();
			}
		}
		return list;
	}
	
	public List<String> getRemoteList(List<String> list){
		List<String> retList=new ArrayList<String>();
		try{
			if(list!=null){
				for(String str: list){
					retList.add(str.substring(Constants.ORGIN.length()));					
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		return retList;
	}
	
	public List<String> getListOfTags(String path, String userName){
		List<String> retList=new ArrayList<String>();
		try{
			Repository repo = this.getRepository(path, userName);
			
			Map<String, Ref> ref = repo.getTags();
			Set<Entry<String, Ref>> entry = ref.entrySet();
			for(Entry<String, Ref> refentry : entry){
					System.out.println("Key "+refentry.getKey());
					retList.add(refentry.getKey());	
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		return retList;
	}
	
	public List<String> getListOfContributors(String path, String userName){
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
		        System.out.println("rev auth email: "+rev.getAuthorIdent().getEmailAddress());
		        System.out.println("rev auth name: "+rev.getAuthorIdent().getName());
		        System.out.println("rev auth when: "+rev.getAuthorIdent().getWhen());
		        System.out.println("rev auth offcet: "+rev.getAuthorIdent().getTimeZoneOffset());
		        System.out.println("rev time : "+rev.getCommitTime());
		        System.out.println("rev  name: "+rev.name());
		    }
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		return retList;
	}
	public void listRemoteRepo(String path, String userName){
		try{
			Collection<Ref> refs = Git.lsRemoteRepository()
		            .setHeads(true)
		            .setTags(true)
		            .setRemote(path)
		            .call();
		        
		        for(Ref ref : refs) {
		            System.out.println("Ref: " + ref.getName());
		            
		        }
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
}
