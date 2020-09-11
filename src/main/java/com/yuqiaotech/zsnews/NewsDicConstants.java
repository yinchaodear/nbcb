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
    
    /**
     * 分类
     */
    interface ICategory
    {
        interface Type
        {
            String QUANJU = "全局";
            
            String JUBU = "局部";
        }
    }
    
    /**
     * 素材
     */
    interface IMaterial
    {
        /**
         * 类型
         */
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
    
    /**
     * 新闻
     *
     */
    interface INews
    {
        interface Status
        {
            /**
             * 已上架
             */
            Integer UP = 0;
            
            /**
             * 下架状态
             */
            Integer DOWN = 1;
            
            /**
             * 处理中
             */
            Integer DOING = 2;
            
            /**
             * 审核中
             */
            Integer CHECKING = 3;
            
            /**
             * 已失败
             */
            Integer FAIL = 4;
            
        }
    }
    
    /**
     * 系统消息
     *
     */
    interface INotice
    {
        /**
         * 消息类型
         */
        interface Type
        {
            /**
             * 系统消息
             */
            Integer SYS = 1;
            
            Integer OTHER = 2;
        }
        
        /**
         * 
         * 推送形式
         */
        interface Way
        {
            /**
             * 短信
             */
            Integer SMS = 1;
            
            /**
             * PUSH
             */
            Integer PUSH = 2;
        }
        
        /**
         * 消息状态
         */
        interface Status
        {
            /**
             * 处理中
             */
            Integer DOING = 1;
            
            /**
             * 已完成
             */
            Integer END = 2;
        }
    }
}
