package com.leo.checkin.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @Description:
 * @Author: leo
 * @Date: 2021/9/24 22:14
 */
@ApiModel(value = "TaskEnableInputDTO")
@Data
public class TaskEnableInputDTO {
    private String stuNum;

    private Integer checkEnable;
}
