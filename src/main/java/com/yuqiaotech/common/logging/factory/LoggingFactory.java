package com.yuqiaotech.common.logging.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.yuqiaotech.common.logging.domain.Logging;
import com.yuqiaotech.common.web.base.BaseRepository;

/**
 * 实 现 一 部 工 厂 机 制 -- [就眠仪式]
 * */
@Component
public class LoggingFactory
{
    @Autowired
    private BaseRepository<Logging, Long> loggingRespository;
    
    /**
     * 执 行 日 志 入 库 操 作
     * */
    @Async
    public void record(Logging logging)
    {
        loggingRespository.save(logging);
    }
    
}
