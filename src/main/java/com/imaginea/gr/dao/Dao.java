package com.imaginea.gr.dao;

import java.io.Serializable;
import java.util.Hashtable;

import org.springframework.dao.DataAccessException;

import com.imaginea.gr.entity.Entity;

public interface Dao<E extends Entity,K extends Serializable> {

	public Long save(E inEntity) throws DataAccessException ;

	public <E extends Entity> E find(Class<E> inEntityClass, K inPkey) throws DataAccessException;
	
	public <E extends Entity, obj extends Object> E getEntity(Class<E> inElementClass, String queryName, Hashtable<String, obj> criteria) throws DataAccessException;
	
	public void delete(Entity inEntity)	throws DataAccessException;
	
}
