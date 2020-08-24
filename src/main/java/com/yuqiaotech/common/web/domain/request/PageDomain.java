package com.yuqiaotech.common.web.domain.request;

/**
 * Describe: 分 页 参 数 封 装
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 * */
public class PageDomain
{
    
    /**
     * 当前页
     * */
    private Integer page = 1;
    
    /**
     * 每页数量
     * */
    private Integer limit = 10;
    
    public Integer getPage()
    {
        return page;
    }
    
    public void setPage(Integer page)
    {
        this.page = page;
    }
    
    public Integer getLimit()
    {
        return limit;
    }
    
    public void setLimit(Integer limit)
    {
        this.limit = limit;
    }
    
}
