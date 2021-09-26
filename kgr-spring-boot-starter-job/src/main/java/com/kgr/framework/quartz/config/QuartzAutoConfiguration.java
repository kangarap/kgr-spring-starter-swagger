package com.kgr.framework.quartz.config;

import com.kgr.framework.quartz.core.scheduler.SchedulerManager;
import org.quartz.Scheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling // 开启 Spring 自带的定时任务
public class QuartzAutoConfiguration {

    @Bean
    public SchedulerManager schedulerManager(Scheduler scheduler) {
        return new SchedulerManager(scheduler);
    }

}
