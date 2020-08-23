package com.yuqiaotech.common.web.base;

import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.transform.ResultTransformer;

import com.yuqiaotech.common.web.domain.dao.PaginationSupport;

/**
 * 分页的时候有时需要输入页码和页长，有时需要输入开始记录，为了区分，
 * 前者的方法名为paginate开头，后者以findPage开头，以示区别。
 * 
 */
public interface PageRepository
{
    //----------------------by criteria---------------------
    PaginationSupport findPageByCriteria(DetachedCriteria detachedCriteria, int startIndex, int pageSize);
    
    PaginationSupport findPageByCriteria(DetachedCriteria detachedCriteria, int startIndex, int pageSize,
        ResultTransformer resultTransformer);
    
    PaginationSupport paginateByCriteria(DetachedCriteria detachedCriteria, int pageNo, int pageSize,
        ResultTransformer resultTransformer);
    
    PaginationSupport paginateByCriteria(DetachedCriteria detachedCriteria, int pageNo, int pageSize);
    
    /**
     * 执行一条原始sql，并进行分页。
     * 
     * @param nativeSql sql语句
     * @param entities 转换成什么实体类
     * @param params 参数
     * @param pageNo 页码
     * @param pageSize 查出多少条
     * @return
     */
    PaginationSupport paginateByNativeSql(String nativeSql, Class[] entities, Map<String, Object> params, int pageNo,
        int pageSize);
    
    PaginationSupport paginateByNativeSql(String nativeSql, Class entityType, Map<String, Object> params, int pageNo,
        int pageSize);
    
    PaginationSupport paginateByNativeSql(String nativeSql, Map<String, Object> params, int pageNo, int pageSize);
    
    /**
     * 分页，并且把查询结果转化为Map的list。
     * 
     * @param nativeSql
     * @param params
     * @param startIndex
     * @param pageSize
     * @return
     */
    PaginationSupport paginateAsMapByNativeSql(String nativeSql, Map<String, Object> params, int pageNo, int pageSize);
    
    //----------------------by hql----------------------
    PaginationSupport findPageByHql(String hql, int startIndex, int pageSize);
    
    PaginationSupport findPageByHql(String hql, int startIndex, int pageSize, Map<String, Object> params);
    
    PaginationSupport findPageByHql(String hql, String vCountHql, int startIndex, Map<String, Object> params);
    
    PaginationSupport findPageByHql(String hql, String vCountHql, int startIndex, int pageSize,
        Map<String, Object> params);
    
    PaginationSupport paginateByHql(String hql, int pageNo, int pageSize, Map<String, Object> params);
    
}
