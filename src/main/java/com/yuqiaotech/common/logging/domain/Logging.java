package com.yuqiaotech.common.logging.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.yuqiaotech.common.logging.enums.BusinessType;
import com.yuqiaotech.common.logging.enums.RequestMethod;

/**
 * 日志组件
 * */
@Entity
public class Logging
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * 标题
     * */
    private String title;
    
    /**
     * 描述
     * */
    private String description;
    
    /**
     * 业务类型
     * */
    private BusinessType businessType;
    
    /**
     * 请求方式
     * */
    private RequestMethod requestMethod;
    
    /**
     * 请求的方法
     * */
    private String method;
    
    /**
     * 请求的连接
     * */
    private String operateUrl;
    
    /**
     * 用户 IP 地址
     * */
    private String operateAddress;
    
    /**
     * 请 求 参 数
     * */
    private String requestParam;
    
    /**
     * 获 取 请 求 体
     * */
    private String requestBody;
    
    /**
     * 接 口 响 应 数 据
     * */
    private String responseBody;
    
    /**
     * 接 口 执 行 状 态
     * */
    private boolean success;
    
    /**
     * 异 常 信 息
     * */
    private String errorMsg;
    
    /**
     * 使用浏览器
     * */
    private String Browser;
    
    /**
     * 操作系统
     * */
    private String systemOs;
    
    /**
     * 操 作 时 间
     * */
    private Date createTime;
    
    /**
     * 操 作 人 员 名 称
     * */
    private String operateName;
    
    /**
     * 操作人员ID
     */
    private Long operateId;
    
    /**
     * 扩 展 信 息
     * */
    @Transient
    private Map<String, String> map = new HashMap<>();
    
    public Long getId()
    {
        return id;
    }
    
    public void setId(Long id)
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
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public BusinessType getBusinessType()
    {
        return businessType;
    }
    
    public void setBusinessType(BusinessType businessType)
    {
        this.businessType = businessType;
    }
    
    public RequestMethod getRequestMethod()
    {
        return requestMethod;
    }
    
    public void setRequestMethod(RequestMethod requestMethod)
    {
        this.requestMethod = requestMethod;
    }
    
    public String getMethod()
    {
        return method;
    }
    
    public void setMethod(String method)
    {
        this.method = method;
    }
    
    public String getOperateUrl()
    {
        return operateUrl;
    }
    
    public void setOperateUrl(String operateUrl)
    {
        this.operateUrl = operateUrl;
    }
    
    public String getOperateAddress()
    {
        return operateAddress;
    }
    
    public void setOperateAddress(String operateAddress)
    {
        this.operateAddress = operateAddress;
    }
    
    public String getRequestParam()
    {
        return requestParam;
    }
    
    public void setRequestParam(String requestParam)
    {
        this.requestParam = requestParam;
    }
    
    public String getRequestBody()
    {
        return requestBody;
    }
    
    public void setRequestBody(String requestBody)
    {
        this.requestBody = requestBody;
    }
    
    public String getResponseBody()
    {
        return responseBody;
    }
    
    public void setResponseBody(String responseBody)
    {
        this.responseBody = responseBody;
    }
    
    public boolean isSuccess()
    {
        return success;
    }
    
    public void setSuccess(boolean success)
    {
        this.success = success;
    }
    
    public String getErrorMsg()
    {
        return errorMsg;
    }
    
    public void setErrorMsg(String errorMsg)
    {
        this.errorMsg = errorMsg;
    }
    
    public String getBrowser()
    {
        return Browser;
    }
    
    public void setBrowser(String browser)
    {
        Browser = browser;
    }
    
    public String getSystemOs()
    {
        return systemOs;
    }
    
    public void setSystemOs(String systemOs)
    {
        this.systemOs = systemOs;
    }
    
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    public String getOperateName()
    {
        return operateName;
    }
    
    public void setOperateName(String operateName)
    {
        this.operateName = operateName;
    }
    
    public Map<String, String> getMap()
    {
        return map;
    }
    
    public void setMap(Map<String, String> map)
    {
        this.map = map;
    }
    
    public Long getOperateId()
    {
        return operateId;
    }
    
    public void setOperateId(Long operateId)
    {
        this.operateId = operateId;
    }
}
