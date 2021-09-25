package com.leo.checkin.controller;

import com.leo.checkin.dto.TaskEnableInputDTO;
import com.leo.checkin.service.QuartzService;
import com.leo.checkin.util.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description:
 * @Author: leo
 * @Date: 2021/9/15 23:16
 */
@Api(tags = "定时任务控制器")
@RestController
@RequestMapping("/task")
public class QuartzController {
    @Autowired
    private QuartzService quartzService;
    @PostMapping("/enable")
    public Result<Boolean> enableTask(@RequestBody @Validated TaskEnableInputDTO inputDTO, HttpServletRequest request) {
        quartzService.enableTask(inputDTO, request.getHeader("tk"));
        return Result.success();
    }

}
