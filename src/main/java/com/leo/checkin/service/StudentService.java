package com.leo.checkin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leo.checkin.dto.CheckInfoUpdateInputDTO;
import com.leo.checkin.dto.StudentLoginInputDTO;
import com.leo.checkin.dto.StudentLoginOutputDTO;
import com.leo.checkin.model.Student;

/**
 * @Description:
 * @Author: leo
 * @Date: 2021/9/12 23:46
 */
public interface StudentService extends IService<Student> {
    /**
     *
     * @param inputDTO
     * @return
     */
    StudentLoginOutputDTO login(StudentLoginInputDTO inputDTO);

    /**
     *
     * @param inputDTO
     * @param token
     * @return
     */
    boolean setCheckinfo(CheckInfoUpdateInputDTO inputDTO, String token);
}
