package com.imaginea.gr.service;

import java.util.Map;

import com.imaginea.gr.entity.User;
import com.imaginea.gr.exception.GitReplicaException;

public interface LoginService {

	public String addUser(User user) throws GitReplicaException;
	public User getUser(User user) throws GitReplicaException;
	public User getUser(String userName) throws GitReplicaException;
	public Map<String, String> getRoles()throws GitReplicaException;
}
