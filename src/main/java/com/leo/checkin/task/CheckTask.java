package com.leo.checkin.task;

import com.leo.checkin.exception.AppException;
import com.leo.checkin.util.Checkin;
import com.leo.checkin.util.MailUtil;
import com.leo.checkin.util.QuartzUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.io.IOException;
import java.util.Map;

/**
 * @Description:
 * @Author: leo
 * @Date: 2021/9/14 23:33
 */
@ConditionalOnProperty(prefix = "load", name = "taskname", havingValue = "CheckTask")
@Slf4j
public class CheckTask extends QuartzJobBean implements MyTask{
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        Map data = context.getTrigger().getJobDataMap().getWrappedMap();
        try {
            Map cookie = Checkin.login(data.get("stuNum").toString(), data.get("stuPass").toString());
            if (Checkin.submit(cookie, data)) {
                if (StringUtils.isNotEmpty(data.get("mail").toString())) {
                    MailUtil.sendMail(data.get("mail").toString(), "打卡成功", "打卡成功");
                }
            } else {
                if (StringUtils.isNotEmpty(data.get("mail").toString())) {
                    MailUtil.sendMail(data.get("mail").toString(), "打卡失败", "打卡失败");
                }
            }
        } catch (IOException e) {
            log.error("IOException:{}",e.getMessage());
            QuartzUtil.removeJobs(data.get("stuNum").toString(), data.get("stuPass").toString());
        } catch (AppException e) {
            log.error("AppException:{}", e.getMessage());
            QuartzUtil.removeJobs(data.get("stuNum").toString(), data.get("stuPass").toString());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Exception:{}", e.getMessage());
            QuartzUtil.removeJobs(data.get("stuNum").toString(), data.get("stuPass").toString());
        }

    }
}
