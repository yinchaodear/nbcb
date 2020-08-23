package com.yuqiaotech.common.logging.annotation;

import java.lang.annotation.*;

import com.yuqiaotech.common.logging.enums.BusinessType;

/**
 * 系 统 日 志 注 解
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface Logging {

    /**
     * 默认无参输入
     * */
    String value() default "暂无标题";

    /**
     * Title 默认输入
     * */
    String title() default "暂无标题";

    /**
     * Describe 默认输入
     * */
    String describe() default "暂无介绍";

    /**
     * 业 务 类 型  默认Query
     * */
    BusinessType type() default BusinessType.QUERY;
}
