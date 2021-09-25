package com.leo.checkin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leo.checkin.dao.StudentMapper;
import com.leo.checkin.dto.TaskEnableInputDTO;
import com.leo.checkin.exception.AppException;
import com.leo.checkin.model.Student;
import com.leo.checkin.service.QuartzService;
import com.leo.checkin.task.MyTask;
import com.leo.checkin.util.MailUtil;
import com.leo.checkin.util.QuartzUtil;
import com.leo.checkin.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author: leo
 * @Date: 2021/9/14 23:37
 */
@Slf4j
@Service
public class QuartzServiceImpl implements QuartzService {
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private MyTask task;

    @Override
    public void jobChange(String username) {
        // 查询数据库
        Student student = studentMapper.selectById(username);

        // 判断是否需要执行
        if (student.getCheckEnable() == 0 || StringUtils.isBlank(student.getCron())) {
            QuartzUtil.removeJobs(student.getStuNum(), student.getStuPass());
            return;
        }
        // 更新定时任务
        QuartzUtil.updateJobs(task.getClass(), student.getStuNum(), student.getStuPass(), student.getCron(),  student);
    }

    @Override
    public void startAllJobs() {
        QueryWrapper<Student> queryWrapper = new QueryWrapper();
        queryWrapper.eq("check_enable", "1");
        List<Student> students = studentMapper.selectList(queryWrapper);
        for (Student item : students) {
            try {
                QuartzUtil.updateJobs(task.getClass(), item.getStuNum(), item.getStuPass(), item.getCron(), item);
            } catch (RuntimeException e) {
                QuartzUtil.removeJobs(item.getStuNum(), item.getStuPass());
                log.error("CronException:{}", e.getMessage());
                if (StringUtils.isNotEmpty(item.getMail())) {
                    MailUtil.sendMail(item.getMail(), "打卡失败", "打卡失败:cron错误");
                }
            }
        }
    }

    @Override
    public void enableTask(TaskEnableInputDTO inputDTO, String token) {
        String username = TokenUtil.getUsernameFromToken(token);
        if (!inputDTO.getStuNum().equals(username)) {
            throw new AppException("你没有权限操作");
        }
        Student stu = new Student();
        stu.setStuNum(inputDTO.getStuNum());
        stu.setCheckEnable(inputDTO.getCheckEnable());
        studentMapper.updateEnable(stu);
        jobChange(username);
    }
}
