package com.yuqiaotech.common.tools.common;

import java.util.Collection;
import java.util.List;

/**
 * 集合工具类
 */
public class CollectionUtils
{
    
    /**
     * 将Collection的内容转换为字符串数组
     * @param colls 需要转换的集合
     * @return String[] 转换后的字符串数组
     */
    public static String[] toStringArray(Collection<String> colls)
    {
        String[] stringArray = new String[0];
        if (colls != null && !colls.isEmpty())
        {
            Object[] objectArray = colls.toArray();
            int length = colls.size();
            stringArray = new String[length];
            
            for (int i = 0; i < length; i++)
            {
                if (objectArray[i] != null)
                {
                    stringArray[i] = objectArray[i].toString();
                }
                else
                {
                    stringArray[i] = "";
                }
            }
        }
        return stringArray;
    }
    
    /**
     * 将集合按照指定分隔符拼接
     * @param colls
     * @param split
     * @return
     */
    public static String join(Collection<String> colls, String split)
    {
        if (colls == null || colls.isEmpty())
        {
            return "";
        }
        String[] array = new String[colls.size()];
        colls.toArray(array);
        return StringUtils.arrayToString(array, split);
    }
    
    /**
     * 从List中取出第一个对象,如果List为空，返回null
     * @param vList vList
     * 
     * @return Object List中第一个对象
     */
    public static Object getFirstObject(List vList)
    {
        if (!isEmpty(vList))
        {
            return vList.get(0);
        }
        else
        {
            return null;
        }
    }
    
    /**
     * 判断List是否为空
     * @param list list
     * 
     * @return boolean list是否为空
     */
    public static boolean isEmpty(Collection list)
    {
        return list == null || list.isEmpty();
    }
    
    /**
     * 判断List是否为空
     * @param list list
     * 
     * @return boolean list是否为空
     */
    public static boolean isNotEmpty(Collection list)
    {
        return !isEmpty(list);
    }
    
}
