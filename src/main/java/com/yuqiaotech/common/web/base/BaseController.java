package com.yuqiaotech.common.web.base;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.yuqiaotech.common.tools.servlet.ServletUtil;
import com.yuqiaotech.common.web.domain.response.ResuTree;
import com.yuqiaotech.common.web.domain.response.Result;
import com.yuqiaotech.common.web.domain.response.ResultTable;


public class BaseController
{
    protected String msg;
    
    protected int page = 1;
    
    protected int rows = 15;

    @Value("${attachmentRoot}")
    public String attachmentRoot;
    
    public String getCurrentUsername()
    {
        return ServletUtil.getCurrentUsername();
    }

    public String getCurrentUserType() {
        return ServletUtil.getCurrentUserType();
    }

    public Long getCurrentUserId()
    {
        return ServletUtil.getCurrentUserId();
    }
    
    public HttpSession getSession()
    {
        return ServletUtil.getSession();
    }
    
    /**
     * 成功操作
     * */
    public Result success()
    {
        
        return Result.success();
    }
    
    /**
     * 成功操作
     * */
    public Result success(String msg)
    {
        
        return Result.success(msg);
    }
    
    /**
     * 成功操作
     * */
    public Result success(Object data)
    {
        
        return Result.success(data);
    }
    
    /**
     * 成功操作
     * */
    public Result success(String msg, Object data)
    {
        
        return Result.success(msg, data);
    }
    
    /**
     * 成功操作
     * */
    public Result success(int code, String message, Object data)
    {
        
        return Result.success(code, message, data);
    }
    
    /**
     * 失败操作
     * */
    public Result failure()
    {
        
        return Result.failure();
    }
    
    /**
     * 失败操作
     * */
    public Result failure(String msg)
    {
        
        return Result.failure(msg);
    }
    
    /**
     * 失败操作
     * */
    public Result failure(String msg, Object data)
    {
        
        return Result.failure(msg, data);
    }
    
    /**
     * 失败操作
     * */
    public Result failure(int code, String msg, Object data)
    {
        
        return Result.failure(code, msg, data);
    }
    
    /**
     * 选择返回
     * */
    public Result decide(Boolean b)
    {
        
        return Result.decide(b);
    }
    
    /**
     * 选择返回
     * */
    public Result decide(Boolean b, String success, String failure)
    {
        
        return Result.decide(b, success, failure);
    }
    
    /**
     * 页面跳转
     * */
    public ModelAndView JumpPage(String path)
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(path);
        return modelAndView;
    }
    
    /**
     * 带参数的页面跳转
     * */
    public ModelAndView JumpPage(String path, Map<String, ?> params)
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(path);
        modelAndView.addAllObjects(params);
        return modelAndView;
    }
    
    /**
     * Describe: 返回 Tree 数据
     * Param data
     * Return Tree数据
     * */
    protected static ResuTree dataTree(Object data)
    {
        ResuTree resuTree = new ResuTree();
        resuTree.setData(data);
        return resuTree;
    }
    
    /**
     * Describe: 返回数据表格数据 分页
     * Param data
     * Return 表格分页数据
     * */
    protected static ResultTable pageTable(Object data, long count)
    {
        
        return ResultTable.pageTable(count, data);
    }
    
    /**
     * Describe: 返回数据表格数据
     * Param data
     * Return 表格分页数据
     * */
    protected static ResultTable dataTable(Object data)
    {
        
        return ResultTable.dataTable(data);
    }
    
    /**
     * Describe: 返回树状表格数据 分页
     * Param data
     * Return 表格分页数据
     * */
    protected static ResultTable treeTable(Object data)
    {
        
        return ResultTable.dataTable(data);
    }
    
    protected static String[] getNullPropertyNames(Object source)
    {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
        
        Set<String> emptyNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds)
        {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null)
                emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }


    protected void responseWrite(String str) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();

        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        try {
            response.getWriter().write(str);
        } catch (IOException var3) {
            throw new RuntimeException(var3.getMessage(), var3);
        }
    }
}
