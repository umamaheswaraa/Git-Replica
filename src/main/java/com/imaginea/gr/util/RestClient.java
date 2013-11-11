package com.imaginea.gr.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;

public class RestClient {

	public String getResponse(String url){
		
		return this.executeMethod(url);
	}
	
	public String executeMethod(String url){
		HttpMethod httpMethod=null;
		String result=null;
		try{			
			HttpClient client = new HttpClient();
			httpMethod = new GetMethod(url);
			int resultCode = client.executeMethod(httpMethod);
			if(resultCode == 400){
				throw new Exception("Received Bad error from URL");
			}
			result = httpMethod.getResponseBodyAsString();
				
		}catch (Exception e) {
			// TODO: handle exception
		}finally{
			if(httpMethod!=null)
				httpMethod.releaseConnection();
		}	
		return result;
	}
}
