package com.leo.checkin.listener;

import com.leo.checkin.service.QuartzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: leo
 * @Date: 2021/9/15 0:15
 */
@Component
public class ApplicationStartListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private QuartzService quartzService;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent applicationEvent) {
        if(applicationEvent.getApplicationContext().getParent() == null) {
            quartzService.startAllJobs();
        }

    }
}
