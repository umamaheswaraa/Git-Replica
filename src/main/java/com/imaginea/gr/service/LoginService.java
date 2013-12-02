package com.imaginea.gr.service;

import java.util.Map;

import com.imaginea.gr.entity.User;
import com.imaginea.gr.exception.GitReplicaException;

public interface LoginService {

    String addUser(User user) throws GitReplicaException;
    User getUser(User user) throws GitReplicaException;
    User getUser(String userName) throws GitReplicaException;
    Map<String, String> getRoles()throws GitReplicaException;
}
