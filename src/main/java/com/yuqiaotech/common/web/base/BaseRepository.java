package com.yuqiaotech.common.web.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

public interface BaseRepository<T, PK extends Serializable> extends PageRepository
{
    T save(T entity);
    
    void update(T entity);
    
    void delete(T entity);
    
    void remove(PK id, Class<T> clazz);
    
    List findByHql(String hql, Map<String, Object> params);
    
    List findByHql(String hql);
    
    List findByHql(String hql, int firstResult, int maxResults, Map<String, Object> params);
    
    List findByHql(String hql, int firstResult, int maxResults);
    
    List<Map<String, Object>> findMapByNativeSql(String sql);
    
    List<Map<String, Object>> findMapByNativeSql(String sql, final Map<String, Object> params);
    
    List findByCriteria(DetachedCriteria dc);
    
    T get(PK id, Class<T> clazz);
    
    public T findUniqueBy(String propertyName, Object value, Class<T> clazz);
    
    int executeUpdate(String hql, Map<String, Object> params);
    
    int executeUpdate(String hql, Object[] params);
    
    /**
     * 针对返回一条数据的情况。
     * 
     * @param hql
     * @param params
     * @return
     */
    T queryUniqueResult(String hql, Map<String, Object> params);
    
    /**
     * 执行一个DetachedCriteria，获取单条数据。
     * @param detachedCriteria
     * @return
     */
    T queryUniqueResult(final DetachedCriteria detachedCriteria);
    
    /**
     * 执行修改数据的sql。
     * @param nativeSql
     * @param params
     * @return
     */
    int executeUpdateByNativeSql(String nativeSql, Map<String, Object> params);
    
}
