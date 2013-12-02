package com.imaginea.gr.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.imaginea.gr.entity.Entity;
import com.imaginea.gr.util.Utility;

/**
 * @author umamaheswaraa
 *
 * @param <E>
 * @param <K>
 */
public class GenericJpaDao<E extends Entity, K extends Serializable> implements Dao<E,K>{

    private EntityManager entityManager;
    
    public EntityManager getEntityManager() {
        return entityManager;
    }
    /**
     * @param entityManager
     */
    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    /**
     * Save the Entity into the DB
     * @param E
     * @return Long
     */
    public Long save(E inEntity) {
        Long pkey=null;
        if(inEntity.getIsDeleted() == null){
            inEntity.setIsDeleted(false);
        }

        Date today = Utility.getCurrentTime();
        inEntity.setCreatedOn(today);
        inEntity.setChangedOn(today);

        entityManager.persist(inEntity);
        if(entityManager.getEntityManagerFactory()!=null && entityManager.getEntityManagerFactory().getPersistenceUnitUtil()!=null){
            pkey = (Long) entityManager.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(inEntity);
        }
        return pkey;
    }
    
    /**
     * Find the Entity for pkey
     * @param inEntityClass
     * @param inPkey
     * @return Entity
     */
    public Entity find(Class inEntityClass, Serializable inPkey){
        return entityManager.find(inEntityClass, inPkey);
    }
    
    /**
     * Removes the Entity from DB
     * @param inEntity
     */
    public void delete(Entity inEntity){
            entityManager.remove(inEntity);
    }
    
    /**
     * Get the Entity for query
     * @param inEntityClass
     * @param queryName
     * @param criteria
     * @return Entity
     */
    public <E extends Entity, obj extends Object> E getEntity(Class<E> inElementClass, String queryName, Hashtable<String, obj> criteria){
        String key;
        Object result;
        Query query = entityManager.createNamedQuery(queryName);
        if(criteria!=null){
            Enumeration<String> keys = criteria.keys();
            while(keys.hasMoreElements()){
                key = keys.nextElement();
                query.setParameter(key,criteria.get(key));
            }
        }
        
        result = query.getSingleResult();
        return (E) result;
    }
    
}
