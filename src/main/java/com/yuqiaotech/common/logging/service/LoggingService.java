package com.yuqiaotech.common.logging.service;

import java.util.List;

import com.yuqiaotech.common.logging.domain.Logging;

public interface LoggingService {

    /**
     * 执 行 插 入 操 作
     * */
    boolean save(Logging logging);

    /**
     * 执 行 查 询 操 作
     * */
    List<Logging> data();
}
