package com.yuqiaotech.common.tools.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串处理工具类
 */
public class StringUtils
{
    /**
    * 验证数字的正则表达式
    */
    private static final String INT_EXP = "^\\d+$";
    
    /**
     * 根据split拆分字符串,将字符数组拼接成字符串
     * <功能详细描述>
     * 
     * @param str 字符串数组
     * @param split 分割符号
     * @return 返回拆分结果
     */
    public static String arrayToString(String[] str, String split)
    {
        StringBuffer sb = new StringBuffer();
        
        if (isArrayEmpty(str))
        {
            return "";
        }
        
        for (String string : str)
        {
            sb.append(null == string ? "" : string).append(split);
        }
        
        return sb.toString().substring(0, sb.toString().lastIndexOf(split));
    }
    
    /**
    * 是否为空，不能为null，以及任何为空的字符
    * <功能详细描述>
    * 
    * @param str 需要判断的字符串
    * @return boolean 是否为空结果
    * @see [类、类#方法、类#成员]
    */
    public static boolean isBlank(String str)
    {
        if (null == str || "".equals(str.trim()))
        {
            return true;
        }
        return false;
    }
    
    /** 判断字符串是否为空
     * <功能详细描述>
     * @param str 需要判断的字符串
     * @return 为空 true 不可空 false
     * @see [类、类#方法、类#成员]
     */
    public static boolean isEmpty(String str)
    {
        return str == null || str.length() == 0;
    }
    
    /** 判断字符串是否为空
     * <功能详细描述>
     * @param str 需要判断的字符串
     * @return 不为空 true 为空 false
     * @see [类、类#方法、类#成员]
     */
    public static boolean isNotEmpty(String str)
    {
        return !isEmpty(str);
    }
    
    /** 判断字符串是否全是数字
     * <功能详细描述>
     * @param str
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static boolean isNumber(String str)
    {
        return isNotEmpty(str) && str.matches(INT_EXP);
    }
    
    /**
     * 将null转换为空字符串""
     * 
     * @param objStr 转换前字符串
     * @return String 转换后字符串
     */
    public static String parseNull(Object objStr)
    {
        return objStr == null ? "" : objStr.toString();
    }
    
    public static int parseInt(String strValue)
    {
        if (isEmpty(strValue))
        {
            return 0;
        }
        int iValue = 0;
        try
        {
            iValue = new java.lang.Integer(strValue.trim()).intValue();
        }
        catch (Exception ex)
        {
            iValue = 0;
        }
        return iValue;
        
    }
    
    /**判断数组是否为空
     * <功能详细描述>
     * @param array 数组
     * @return boolean true/false
     * @see [类、类#方法、类#成员]
     */
    public static boolean isArrayEmpty(Object[] array)
    {
        return array == null || array.length == 0;
    }
    
    /** 判断字符串是否在数组中
     * <功能详细描述>
     * @param strArray 数组
     * @param str 字符串
     * @return 在则返回true 否则返回false
     * @see [类、类#方法、类#成员]
     */
    public static boolean isStrInStrArray(String[] strArray, String str)
    {
        if (isArrayEmpty(strArray) || isEmpty(str))
        {
            return false;
        }
        
        for (String tempStr : strArray)
        {
            if (str.equals(tempStr))
            {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * 判断字符串数组及其内部是否存在空字符串.
     * <功能详细描述>
     * 
     * @param strs 要检查的字符串数组 
     * @return 是否存在空
     * @see [类、类#方法、类#成员]
     */
    public static boolean isAnyElmentBlank(String[] strs)
    {
        if (null == strs)
        {
            return true;
        }
        
        if (strs.length > 0)
        {
            for (String string : strs)
            {
                if (isBlank(string))
                {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static boolean checkEmail(String email)
    {
        boolean flag = false;
        try
        {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        }
        catch (Exception e)
        {
            flag = false;
        }
        return flag;
    }
    
    public static boolean checkMobileNumber(String mobileNumber)
    {
        boolean flag = false;
        try
        {
            Pattern regex =
                Pattern.compile("^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9])|(17[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
            Matcher matcher = regex.matcher(mobileNumber);
            flag = matcher.matches();
        }
        catch (Exception e)
        {
            flag = false;
        }
        return flag;
    }
    
    public static boolean isContainChinese(String str)
    {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find())
        {
            return true;
        }
        return false;
    }
    
    public static boolean isContainsEnglish(String str)
    {
        Pattern p = Pattern.compile("[a-zA-z]");
        Matcher m = p.matcher(str);
        if (m.find())
        {
            return true;
        }
        return false;
    }
}
