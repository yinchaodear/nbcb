package com.yuqiaotech.common.web.base.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.yuqiaotech.common.web.base.BaseRepository;
import com.yuqiaotech.common.web.domain.dao.PaginationSupport;

@Repository
public class BaseRepositoryImpl<T, PK extends Serializable> implements BaseRepository<T, PK>
{
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    @Transactional
    public T save(T entity)
    {
        entityManager.persist(entity);
        return entity;
    }
    
    @Override
    @Transactional
    public void update(T entity)
    {
        entityManager.merge(entity);
        entityManager.flush();
    }
    
    @Override
    @Transactional
    public void delete(T entity)
    {
        entityManager.remove(entity);
        entityManager.flush();
    }
    
    @Override
    @Transactional
    public void remove(PK id, Class<T> clazz)
    {
        delete(findUniqueBy("id", id, clazz));
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<T> findByHql(String hql, Map<String, Object> params)
    {
        Query query = entityManager.createQuery(hql);
        if (params != null && !params.isEmpty())
        {
            for (Map.Entry<String, Object> entry : params.entrySet())
            {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }
        return query.getResultList();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<T> findByHql(String hql)
    {
        return findByHql(hql, null);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<T> findByHql(String hql, int firstResult, int maxResults, Map<String, Object> params)
    {
        Query query = entityManager.createQuery(hql);
        if (params != null && !params.isEmpty())
        {
            for (Map.Entry<String, Object> entry : params.entrySet())
            {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);
        
        return query.getResultList();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<T> findByHql(String hql, int firstResult, int maxResults)
    {
        return findByHql(hql, firstResult, maxResults, null);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> findMapByNativeSql(String sql)
    {
        return findMapByNativeSql(sql, null);
    }
    
    @SuppressWarnings("deprecation")
    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> findMapByNativeSql(String sql, Map<String, Object> params)
    {
        Query query = entityManager.createNativeQuery(sql);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        if (params != null && !params.isEmpty())
        {
            for (Map.Entry<String, Object> entry : params.entrySet())
            {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }
        return query.getResultList();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    @Transactional(readOnly = true)
    public List<T> findByCriteria(DetachedCriteria criteria)
    {
        Criteria execute = criteria.getExecutableCriteria((Session)entityManager.getDelegate());
        System.out.println(execute.toString());
        return execute.list();
    }
    
    @Override
    @Transactional(readOnly = true)
    public T get(PK id, Class<T> clazz)
    {
        return findUniqueBy("id", id, clazz);
    }
    
    @Override
    @Transactional(readOnly = true)
    public T findUniqueBy(String propertyName, Object value, Class<T> clazz)
    {
        DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
        criteria.add(Restrictions.eq(propertyName, value));
        List<T> list = findByCriteria(criteria);
        if (list != null && !list.isEmpty())
        {
            return list.get(0);
        }
        return null;
    }
    
    @Override
    @Transactional(readOnly = true)
    public T queryUniqueResult(String hql, Map<String, Object> params)
    {
        List<T> list = findByHql(hql, params);
        if (list != null && !list.isEmpty())
        {
            return list.get(0);
        }
        return null;
    }
    
    @Override
    @Transactional(readOnly = true)
    public T queryUniqueResult(DetachedCriteria criteria)
    {
        List<T> list = findByCriteria(criteria);
        if (list != null && !list.isEmpty())
        {
            return list.get(0);
        }
        return null;
    }
    
    @Override
    @Transactional
    public int executeUpdate(String hql, Map<String, Object> params)
    {
        Query query = entityManager.createQuery(hql);
        if (params != null && !params.isEmpty())
        {
            for (Map.Entry<String, Object> entry : params.entrySet())
            {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }
        return query.executeUpdate();
    }
    
    @Override
    public int executeUpdate(String hql, Object[] params)
    {
        Query query = entityManager.createQuery(hql);
        if (params != null && params.length > 0)
        {
            for (int i = 0; i < params.length; i++)
            {
                query.setParameter(i + 1, params[i]);
            }
        }
        return query.executeUpdate();
    }
    
    @Override
    @Transactional
    public int executeUpdateByNativeSql(String nativeSql, Map<String, Object> params)
    {
        Query query = entityManager.createNativeQuery(nativeSql);
        if (params != null && !params.isEmpty())
        {
            for (Map.Entry<String, Object> entry : params.entrySet())
            {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }
        return query.executeUpdate();
    }
    
    @Override
    public PaginationSupport findPageByCriteria(DetachedCriteria detachedCriteria, int startIndex, int pageSize)
    {
        return findPageByCriteria(detachedCriteria, startIndex, pageSize, null);
    }
    
    @Override
    @Transactional(readOnly = true)
    public PaginationSupport findPageByCriteria(DetachedCriteria detachedCriteria, int startIndex, int pageSize,
        ResultTransformer resultTransformer)
    {
        Criteria execute = detachedCriteria.getExecutableCriteria((Session)entityManager.getDelegate());
        List orderEntries = null;
        List newEntrys = new ArrayList();
        Field field = null;
        try
        {
            field = CriteriaImpl.class.getDeclaredField("orderEntries");
            field.setAccessible(true);
            //Get orders
            orderEntries = (List)field.get(execute);
            //Remove orders
            field.set(execute, newEntrys);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        Long totalCount = ((Long)execute.setProjection(Projections.rowCount()).uniqueResult());
        try
        {
            //Add orders return
            if (orderEntries != null)
                newEntrys.addAll(orderEntries);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        if (totalCount == null)
            totalCount = new Long(0);
        execute.setProjection(null);
        if (startIndex > totalCount && totalCount > 0)
        {
            startIndex = (totalCount.intValue() - 1) / pageSize * pageSize;
        }
        if (resultTransformer != null)
            execute.setResultTransformer(resultTransformer);
        List items = execute.setFirstResult(startIndex).setMaxResults(pageSize).list();
        PaginationSupport ps = new PaginationSupport(items, totalCount.intValue(), startIndex, pageSize);
        return ps;
    }
    
    @Override
    @Transactional(readOnly = true)
    public PaginationSupport paginateByCriteria(DetachedCriteria detachedCriteria, int pageNo, int pageSize,
        ResultTransformer resultTransformer)
    {
        int startIndex = (pageNo - 1) * pageSize;
        return findPageByCriteria(detachedCriteria, startIndex, pageSize, resultTransformer);
    }
    
    @Override
    @Transactional(readOnly = true)
    public PaginationSupport paginateByCriteria(DetachedCriteria detachedCriteria, int pageNo, int pageSize)
    {
        
        return paginateByCriteria(detachedCriteria, pageNo, pageSize, null);
    }
    
    @Override
    @Transactional(readOnly = true)
    public PaginationSupport paginateByNativeSql(String nativeSql, Class[] entities, Map<String, Object> params,
        int pageNo, int pageSize)
    {
        int startIndex = (pageNo - 1) * pageSize;
        return paginateByNativeSql(nativeSql, null, entities, params, startIndex, pageSize);
    }
    
    @Override
    @Transactional(readOnly = true)
    public PaginationSupport paginateByNativeSql(String nativeSql, Class entityType, Map<String, Object> params,
        int pageNo, int pageSize)
    {
        int startIndex = (pageNo - 1) * pageSize;
        return paginateByNativeSql(nativeSql, new Class[] {entityType}, params, startIndex, pageSize);
    }
    
    @Transactional(readOnly = true)
    private PaginationSupport paginateByNativeSql(final String nativeSql, final String[] alias,
        final Class[] entityTypes, final Map<String, Object> params, final int startIndex, final int pageSize)
    {
        String countSql = "select count(*) from (" + nativeSql + ") v";
        Query query = entityManager.createNativeQuery(countSql);
        if (params != null && !params.isEmpty())
        {
            for (Map.Entry<String, Object> entry : params.entrySet())
            {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }
        int totalCount = ((Number)query.getSingleResult()).intValue();
        
        query = entityManager.createNativeQuery(nativeSql);
        
        if (params != null && !params.isEmpty())
        {
            for (Map.Entry<String, Object> entry : params.entrySet())
            {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }
        
        if (entityTypes != null && entityTypes.length > 0)
        {
            for (int i = 0; i < entityTypes.length; i++)
            {
                if (alias == null)
                {
                    query.unwrap(NativeQueryImpl.class).addEntity(entityTypes[i]);
                }
                else
                {
                    query.unwrap(NativeQueryImpl.class).addEntity(alias[i], entityTypes[i]);
                }
            }
        }
        
        query.setFirstResult(startIndex);
        query.setMaxResults(pageSize);
        List items = query.getResultList();
        PaginationSupport ps = new PaginationSupport(items, totalCount, startIndex, pageSize);
        return ps;
    }
    
    @Override
    @Transactional(readOnly = true)
    public PaginationSupport paginateByNativeSql(String nativeSql, Map<String, Object> params, int pageNo, int pageSize)
    {
        int startIndex = (pageNo - 1) * pageSize;
        return paginateByNativeSql(nativeSql, new Class[] {}, params, startIndex, pageSize);
    }
    
    @Override
    @Transactional(readOnly = true)
    public PaginationSupport paginateAsMapByNativeSql(String nativeSql, Map<String, Object> params, int pageNo,
        int pageSize)
    {
        int startIndex = (pageNo - 1) * pageSize;
        Query q = entityManager.createNativeQuery("select count(*) from (" + nativeSql + ") v");
        if (params != null && !params.isEmpty())
        {
            for (Map.Entry<String, Object> entry : params.entrySet())
            {
                q.setParameter(entry.getKey(), entry.getValue());
            }
        }
        int totalCount = ((Number)q.getSingleResult()).intValue();
        q = entityManager.createNativeQuery(nativeSql);
        if (params != null && !params.isEmpty())
        {
            for (Map.Entry<String, Object> entry : params.entrySet())
            {
                q.setParameter(entry.getKey(), entry.getValue());
            }
        }
        q.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        q.setFirstResult(startIndex);
        q.setMaxResults(pageSize);
        List items = q.getResultList();
        PaginationSupport ps = new PaginationSupport(items, totalCount, startIndex, pageSize);
        return ps;
    }
    
    @Override
    @Transactional(readOnly = true)
    public PaginationSupport paginateByHql(String hql, int pageNo, int pageSize, Map<String, Object> params)
    {
        int startIndex = (pageNo - 1) * pageSize;
        return findPageByHql(hql, null, startIndex, pageSize, params);
    }
    
    @Override
    @Transactional(readOnly = true)
    public PaginationSupport findPageByHql(String hql, int startIndex, int pageSize)
    {
        return findPageByHql(hql, null, startIndex, pageSize, null);
    }
    
    @Override
    @Transactional(readOnly = true)
    public PaginationSupport findPageByHql(String hql, int startIndex, int pageSize, Map<String, Object> params)
    {
        return findPageByHql(hql, null, startIndex, pageSize, params);
    }
    
    @Override
    @Transactional(readOnly = true)
    public PaginationSupport findPageByHql(String hql, String vCountHql, int startIndex, Map<String, Object> params)
    {
        return findPageByHql(hql, vCountHql, startIndex, 0, params);
    }
    
    @Override
    @Transactional(readOnly = true)
    public PaginationSupport findPageByHql(String hql, String vCountHql, int startIndex, int pageSize,
        Map<String, Object> params)
    {
        Integer totalCount = 0;
        String countHql = vCountHql;
        if (countHql == null)
        {
            countHql = getCountHql(hql);
        }
        Number r = (Number)prepareQuery(countHql, params).getSingleResult();
        if (r != null)
            totalCount = r.intValue();
        if (totalCount == 0)
        {
            return new PaginationSupport(new ArrayList(), 0, startIndex, pageSize);
        }
        Query query = prepareQuery(hql, params);
        if (startIndex > 0)
        {
            query.setFirstResult(startIndex);
        }
        if (pageSize > 0)
        {
            query.setMaxResults(pageSize);
        }
        List items = query.getResultList();
        PaginationSupport ps = new PaginationSupport(items, totalCount, startIndex, pageSize);
        return ps;
    }
    
    private Query prepareQuery(String hql, Map<String, Object> params)
    {
        Query query = entityManager.createQuery(hql);
        if (params != null && !params.isEmpty())
        {
            for (Map.Entry<String, Object> entry : params.entrySet())
            {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }
        return query;
    }
    
    private String getCountHql(String hql)
    {
        int fromIndex = hql.toLowerCase().indexOf("from");
        if (fromIndex < 0)
        {
            throw new RuntimeException("sql里没有from。【" + hql + "】");
        }
        String countHql = hql.substring(fromIndex);
        int orderByIndex = countHql.toLowerCase().indexOf("order by");
        if (orderByIndex != -1)
        {
            countHql = countHql.substring(0, orderByIndex);
        }
        return "select count(*) " + countHql;
    }
    
}
