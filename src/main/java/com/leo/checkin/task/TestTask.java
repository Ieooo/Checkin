package com.leo.checkin.task;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Description:
 * @Author: leo
 * @Date: 2021/9/24 21:51
 */
@Component
@ConditionalOnProperty(prefix = "load", name = "taskname", havingValue = "TestTask")
public class TestTask extends QuartzJobBean implements MyTask{
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        Map data = context.getTrigger().getJobDataMap().getWrappedMap();
        try {
            System.out.println(data.get("stuNum").toString() + "的任务执行");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
