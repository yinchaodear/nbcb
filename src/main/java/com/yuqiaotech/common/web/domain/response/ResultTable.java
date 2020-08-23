package com.yuqiaotech.common.web.domain.response;

import com.yuqiaotech.common.web.base.BaseModel;

import lombok.Data;

/**
 * Describe: 前端表格数据封装
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 * */

@Data
public class ResultTable extends BaseModel {

    /**
     * 状态码
     * */
    private Integer code;

    /**
     * 提示消息
     * */
    private String msg;

    /**
     * 消息总量
     * */
    private Long count;

    /**
     * 数据对象
     * */
    private Object data;

    /**
     * 构 建
     * */
    public static ResultTable pageTable(long count,Object data){
        ResultTable resultTable = new ResultTable();
        resultTable.setData(data);
        resultTable.setCode(0);
        resultTable.setCount(count);
        return resultTable;
    }

    public static ResultTable dataTable(Object data){
        ResultTable resultTable = new ResultTable();
        resultTable.setData(data);
        resultTable.setCode(0);
        return resultTable;
    }

}
