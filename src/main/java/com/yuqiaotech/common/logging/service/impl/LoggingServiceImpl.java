package com.yuqiaotech.common.logging.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yuqiaotech.common.logging.domain.Logging;
import com.yuqiaotech.common.logging.service.LoggingService;

@Service
public class LoggingServiceImpl implements LoggingService
{
    
    @Override
    public boolean save(com.yuqiaotech.common.logging.domain.Logging logging)
    {
        return false;
    }
    
    @Override
    public List<Logging> data()
    {
        return null;
    }
}
