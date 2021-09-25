package com.leo.checkin.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @Description:
 * @Author: leo
 * @Date: 2021/9/18 22:24
 */
@Data
@ApiModel("StudentLoginOutputDTO")
public class StudentLoginOutputDTO {
    private String stuNum;

    private String mail;

    private String destination;

    private String reason;

    private String startDay;

    private String startTime;

    private String endDay;

    private String endTime;

    private String token;
}
