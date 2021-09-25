package com.leo.checkin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Description:
 * @Author: leo
 * @Date: 2021/9/18 21:59
 */
@Data
@ApiModel(value = "退出登录inputDTO")
public class StudentLogoutInputDTO {
    @ApiModelProperty(value = "学号")
    @NotBlank(message = "stuNum-不能为空")
    private String stuNum;
}
