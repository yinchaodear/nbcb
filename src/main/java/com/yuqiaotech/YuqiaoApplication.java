package com.yuqiaotech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware")
public class YuqiaoApplication
{
    /**
     * 启 动 程 序
     * */
    public static void main(String[] args)
    
    {
        SpringApplication.run(YuqiaoApplication.class, args);
    }
}
