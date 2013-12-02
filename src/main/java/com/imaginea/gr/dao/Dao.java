package com.imaginea.gr.dao;

import java.io.Serializable;
import java.util.Hashtable;

import com.imaginea.gr.entity.Entity;

public interface Dao<E extends Entity,K extends Serializable> {

    Long save(E inEntity);
    
    <E extends Entity> E find(Class<E> inEntityClass, K inPkey);
    
    <E extends Entity, obj extends Object> E getEntity(Class<E> inElementClass, String queryName, Hashtable<String, obj> criteria);
    
    void delete(Entity inEntity);
    
}
