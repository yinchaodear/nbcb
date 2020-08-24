package com.yuqiaotech.common.web.domain.response;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe: 前端菜单数据封装信息
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 * */
public class ResuMenu
{
    
    /**
     * 菜单编号
     * */
    private String id;
    
    /**
     * 标题
     * */
    private String title;
    
    /**
     * 菜单类型
     * */
    private String type;
    
    private String openType;
    
    /**
     * 图标
     * */
    private String icon;
    
    /**
     * 跳转路径
     * */
    private String href;
    
    /**
     * 子菜单
     * */
    private List<ResuMenu> children = new ArrayList<>();
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getTitle()
    {
        return title;
    }
    
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    public String getType()
    {
        return type;
    }
    
    public void setType(String type)
    {
        this.type = type;
    }
    
    public String getOpenType()
    {
        return openType;
    }
    
    public void setOpenType(String openType)
    {
        this.openType = openType;
    }
    
    public String getIcon()
    {
        return icon;
    }
    
    public void setIcon(String icon)
    {
        this.icon = icon;
    }
    
    public String getHref()
    {
        return href;
    }
    
    public void setHref(String href)
    {
        this.href = href;
    }
    
    public List<ResuMenu> getChildren()
    {
        return children;
    }
    
    public void setChildren(List<ResuMenu> children)
    {
        this.children = children;
    }
    
}
