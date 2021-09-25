package com.leo.checkin.util;

import com.alibaba.fastjson.JSON;
import com.leo.checkin.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Description:
 * @Author: leo
 * @Date: 2021/9/19 15:16
 */
@Slf4j
@Component
public class QuartzUtil {
    private static Scheduler scheduler;
    @Autowired
    private Scheduler schedule;
    @PostConstruct
    public void init() {
        scheduler = this.schedule;
    }

    /**
     *
     * @param clazz
     * @param name
     * @param group
     * @param cron
     * @param data
     */
    public static void addJobs(Class clazz, String name, String group, String cron, Object data) {
        JobDataMap jobDataMap=new JobDataMap();
        if(data!=null){
            jobDataMap= JSON.parseObject(JSON.toJSONString(data),JobDataMap.class);
        }
        JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(name, group).build();
        try {
            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .usingJobData(jobDataMap)
                    .withIdentity(name, group)
                    .startNow()
                    .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                    .build();
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            log.error("SchedulerException:{}", e.getMessage());
            throw new AppException("定时任务添加失败");
        }
    }

    /**
     *
     * @param name
     * @param group
     */
    public static void removeJobs(String name, String group) {
        JobKey jobKey = new JobKey(name, group);
        try {
            if (scheduler.checkExists(jobKey)){
                scheduler.deleteJob(jobKey);
            }
        } catch (SchedulerException e) {
            log.error("SchedulerException:{}", e.getMessage());
            throw new AppException("定时任务删除失败");
        }
    }

    /**
     *
     * @param clazz
     * @param name
     * @param group
     * @param cron
     * @param data
     */
    public static void updateJobs(Class clazz, String name, String group, String cron, Object data) {
        removeJobs(name, group);
        addJobs(clazz, name, group, cron,data);
    }
}
