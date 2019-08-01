/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.istl.hellospringboot.payload.manager;

import com.istl.hellospringboot.utils.Utils;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author User
 */
@Repository
public class ServiceManagerBean extends BaseDao implements ServiceManager{

    @Override
    public <T> T findById(Class<T> entityClass, Object id, boolean isStatus) {
        try {
            if (isStatus) {
                String className = entityClass.getSimpleName();
                Query query = em.createQuery("select o from " + className + " o where o.id = " + id+" and o.status="+Utils.RECORD_STATUS_ONE);
                return (T) query.getSingleResult();
            } else {
                String className = entityClass.getSimpleName();
                Query query = em.createQuery("select o from " + className + " o where o.id = " + id);
                return (T) query.getSingleResult();
            }
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    public <T> T findById(Class<T> entityClass, Object id) {
        try {
            String className = entityClass.getSimpleName();
            Query query = em.createQuery("select o from " + className + " o where o.id = " + id);

            return (T) query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    public <T> List<T> findByIdIn(Class<T> entityClass, String idList,boolean isStatus) {
        try {
            String className = entityClass.getSimpleName();
            Query query = em.createQuery("select o from " + className + " o where "+(isStatus ? "o.status="+Utils.RECORD_STATUS_ONE+" and ":" ")+"o.id in("+idList+")");

            return (List<T>)query.getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    public <T> T persist(T entity) {
        try {                    
            em.persist(entity);              
        } catch (Throwable t) {        	
            throw new RuntimeException(t);
        }
		
        return (T)entity;
    }

    @Override
    public <T> T update(T entity) {
        try {
            em.merge(entity);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
        
        return (T)entity;
    }

    @Override
    public <T> int deleteById(Class<T> entityClass, Object id) {
        int count = 0;
        try {
            String className = entityClass.getSimpleName();
            Query query = em.createQuery("DELETE FROM " + className + " o WHERE o.id=" + id);
            count = query.executeUpdate();
        } catch (Exception nre) {

        }

        em.clear();

        return count;
    }

    @Override
    public <T> List<T> findByJpqlQuery(String jpql,Object criteria, Class<T> entityClass, Integer startIndex, Integer limit) {
        if(startIndex == null) {
            startIndex = 0;
        }
        
        if(limit == null) {
            limit = 100;
        }
        
        Query q = em.createQuery(jpql,entityClass);
        
        q = Utils.setQueryParameter(q,criteria);
        
        return q.setFirstResult(startIndex * limit).setMaxResults(limit).getResultList();
    }
    
    @Override
    public <T> List<T> findByJpqlQuery(String jpql, Class<T> entityClass) {
        Query q = em.createQuery(jpql,entityClass);        
        return q.getResultList();
    }
    
    @Override
    public <T> List<T> findByJpqlQuery(String jpql, Class<T> entityClass,List<Object> params) {
        Query query = em.createQuery(jpql, entityClass);
        if(params != null && params.size() >0) {
            for(int i=1;i<=params.size();i++) {
                query.setParameter(i, params.get(i-1));
            }
        }
        return query.getResultList();
    }
    
    @Override
    public <T> List<T> findByJpqlQuery(String jpql,Integer startIndex, Integer limit,List<Object> params) {
        Query query = em.createQuery(jpql);
        if(startIndex == null) {
            startIndex = 0;
        }
        if(limit == null || limit <=0) {
            limit = 100;
        }
        if(params != null && params.size() >0) {
            for(int i=1;i<=params.size();i++) {
                query.setParameter(i, params.get(i-1));
            }
        }
        query.setFirstResult(startIndex).setMaxResults(limit);
        return query.getResultList();
    }

    @Override
    public Integer findCountByJpqlQuery(String query,Object criteria) {
        Query q = em.createQuery(query);
        q = Utils.setQueryParameter(q, criteria);
        Long count = (Long) q.getSingleResult();
        return count.intValue();
    }
    
    @Override
    public Integer findCountByJpqlQuery(String query) {
        Long count = (Long)em.createQuery(query).getSingleResult();
        return count.intValue();
    }
    
    
    @Override
    public <T> T findSingleResultByJpqlQuery(String jpql, Class<T> entityClass,List<Object> params) {
        Query query = em.createQuery(jpql, entityClass);
        if(params != null && params.size() >0) {
            for(int i=1;i<=params.size();i++) {
                query.setParameter(i, params.get(i-1));
            }
        }
        try {
            List<T> list = query.setFirstResult(0).setMaxResults(1).getResultList();
            if(list != null && list.size() >0) {
                return list.get(0);
            }
        } catch(NoResultException ne) {
            ne.printStackTrace();
            return null;
        } catch(Exception e){
            e.printStackTrace();
        }
        
        return null;
    }

    @Override
    public EntityManager getEm() {
        return em;
    }
    
    @Override
    public <T> int deleteByJpql(String jpql) {
        int count = 0;
        Query q = em.createQuery(jpql);
        
        try {
            count = q.executeUpdate();
        } catch(Throwable t) {
            t.printStackTrace();
        }
        
        return count;
    }
    
}
