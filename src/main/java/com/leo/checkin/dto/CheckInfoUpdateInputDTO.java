package com.leo.checkin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Description:
 * @Author: leo
 * @Date: 2021/9/19 10:45
 */
@Data
@ApiModel(value = "CheckInfoUpdateInputDTO")
public class CheckInfoUpdateInputDTO {
    @ApiModelProperty(value = "学号")
    @NotBlank(message = "stuNum-不能为空")
    private String stuNum;

    @ApiModelProperty(value = "邮箱")
    private String mail;

    @ApiModelProperty(value = "目的地")
    private String destination;

    @ApiModelProperty(value = "理由")
    private String reason;

    @ApiModelProperty(value = "申请开始日期")
    private String startDay;

    @ApiModelProperty(value = "申请开始时间")
    private String startTime;

    @ApiModelProperty(value = "申请结束日期")
    private String endDay;

    @ApiModelProperty(value = "申请结束时间")
    private String endTime;

    @ApiModelProperty(value = "cron")
    private String cron;
}
