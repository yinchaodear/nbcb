package com.yuqiaotech.zsnews;

/**
 * 浙商新闻常量
 * @author Admin
 *
 */
public interface NewsDicConstants
{
    interface ICommon
    {
        /**
         * 上架状态
         */
        Integer STATUS_UP = 0;
        
        /**
         * 下架状态
         */
        Integer STATUS_DOWN = 1;
        
        /**
         * 未删除
         */
        Integer DELETE_NO = 0;
        
        /**
         * 已删除
         */
        Integer DELETE_YES = 1;
    }
    
    /**
     * 频道常量配置
     */
    interface IChannel
    {
        interface KIND
        {
            /**
             * 频道
             */
            String PD = "频道";
            
            /**
             * 浙商号
             */
            String ZSH = "浙商号";
            
            /**
             * 社区
             */
            String SQ = "社区";
            
            /**
             * 个人
             */
            String GR = "个人";
        }
    }
    
    interface ICategory
    {
        interface Type
        {
            String QUANJU = "全局";
            
            String JUBU = "局部";
        }
    }
    
    interface IMaterial
    {
        interface Type
        {
            /**
             * BANNER
             */
            Integer BANNER = 1;
            
            /**
                                 * 闪屏
             */
            Integer WINDOW = 2;
        }
    }
}
