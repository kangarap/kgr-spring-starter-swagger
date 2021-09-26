package com.kgr.framework.operatelog.config;

import com.kgr.framework.operatelog.core.aop.OperateLogAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OperateLogAutoConfiguration {

    @Bean
    public OperateLogAspect operateLogAspect() {
        return new OperateLogAspect();
    }

}
