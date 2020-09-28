package com.yuqiaotech.sysadmin.bean;

public class ProtectedResourceBean
{
    private Long parentId;
    
    private String checkArr = "0";
    
    private Long id;
    
    private String name;
    
    private String patternStr;
    
    public Long getParentId()
    {
        return parentId;
    }
    
    public void setParentId(Long parentId)
    {
        this.parentId = parentId;
    }
    
    public String getCheckArr()
    {
        return checkArr;
    }
    
    public void setCheckArr(String checkArr)
    {
        this.checkArr = checkArr;
    }
    
    public Long getId()
    {
        return id;
    }
    
    public void setId(Long id)
    {
        this.id = id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getPatternStr()
    {
        return patternStr;
    }
    
    public void setPatternStr(String patternStr)
    {
        this.patternStr = patternStr;
    }
    
}
