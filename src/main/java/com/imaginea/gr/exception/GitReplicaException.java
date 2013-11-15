package com.imaginea.gr.exception;

/**
 * @author umamaheswaraa
 *
 */
public class GitReplicaException extends Exception {

	private static final long serialVersionUID = 5650750549195849364L;

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public GitReplicaException(String message){
		this.message=message;
	}
}
