/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.istl.hellospringboot.payload.manager;

import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author User
 */
public interface ServiceManager {
    EntityManager getEm();
    <T> T findById(Class<T> entityClass, Object id,boolean isStatus);
    <T> T findById(Class<T> entityClass, Object id);
    <T> List<T> findByIdIn(Class<T> entityClass, String idList,boolean isStatus);
    <T> T persist(T entity);    
    <T> T update(T entity);    
    <T> int deleteById(Class<T> entityClass, Object id);
    <T> List<T> findByJpqlQuery(String jpql,Object criteria, Class<T> entityClass, Integer startIndex, Integer limit);
    <T> List<T> findByJpqlQuery(String jpql, Class<T> entityClass);
    <T> List<T> findByJpqlQuery(String jpql, Class<T> entityClass,List<Object> params);
    <T> List<T> findByJpqlQuery(String jpql,Integer startIndex, Integer limit,List<Object> params);
    Integer findCountByJpqlQuery(String query,Object criteria); 
    Integer findCountByJpqlQuery(String query);
    <T> T findSingleResultByJpqlQuery(String jpql, Class<T> entityClass,List<Object> params);
    <T> int deleteByJpql(String jpql);
}
