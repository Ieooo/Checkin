package com.leo.checkin.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @Author: leo
 * @Date: 2021/9/12 23:40
 */
@Data
@TableName("student")
public class Student implements Serializable {
    @TableId(value = "stu_num")
    private String stuNum;

    @TableField("stu_pass")
    private String stuPass;

    private String mail;

    private String destination;

    private String reason;

    @TableField("start_day")
    private String startDay;

    @TableField("start_time")
    private String startTime;

    @TableField("end_day")
    private String endDay;

    @TableField("end_time")
    private String endTime;

    private String cron;

    private Integer checkEnable;
}
