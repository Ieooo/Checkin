package com.leo.checkin.controller;

import com.leo.checkin.annotaion.NoNeedLogin;
import com.leo.checkin.dto.CheckInfoUpdateInputDTO;
import com.leo.checkin.dto.StudentLoginInputDTO;
import com.leo.checkin.dto.StudentLoginOutputDTO;
import com.leo.checkin.service.StudentService;
import com.leo.checkin.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
 * @Date: 2021/9/11 23:02
 */
@Api(tags = "用户控制器")
@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @ApiOperation("登录")
    @NoNeedLogin
    @PostMapping("/login")
    public Result<StudentLoginOutputDTO> login(@RequestBody @Validated StudentLoginInputDTO inputDTO) {
        return Result.success(studentService.login(inputDTO));
    }

    @ApiOperation("修改信息")
    @PostMapping("/modify")
    public Result<Boolean> setCheckInfo(@RequestBody @Validated CheckInfoUpdateInputDTO inputDTO, HttpServletRequest request) {
        studentService.setCheckinfo(inputDTO, request.getHeader("tk"));
        return Result.success();
    }



}
