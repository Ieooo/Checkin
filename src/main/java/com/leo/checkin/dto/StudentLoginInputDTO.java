package com.leo.checkin.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Description:
 * @Author: leo
 * @Date: 2021/9/11 23:16
 */
@Data
public class StudentLoginInputDTO {
    @NotBlank(message = "stuNum不能为空")
    private String stuNum;

    @NotBlank(message = "stuPass不能为空")
    private String stuPass;
}
