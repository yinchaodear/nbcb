package com.yuqiaotech.sysadmin.bean;

import com.yuqiaotech.sysadmin.model.User;

public class UserBean extends User
{
    private String orgid;
    
    private String status;
    
    private String searchstring;
    
    public String getOrgid()
    {
        return orgid;
    }
    
    public void setOrgid(String orgid)
    {
        this.orgid = orgid;
    }
    
    public String getStatus()
    {
        return status;
    }
    
    public void setStatus(String status)
    {
        this.status = status;
    }
    
    public String getSearchstring()
    {
        return searchstring;
    }
    
    public void setSearchstring(String searchstring)
    {
        this.searchstring = searchstring;
    }
}
