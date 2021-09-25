package com.leo.checkin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leo.checkin.dao.StudentMapper;
import com.leo.checkin.dto.CheckInfoUpdateInputDTO;
import com.leo.checkin.dto.StudentLoginInputDTO;
import com.leo.checkin.dto.StudentLoginOutputDTO;
import com.leo.checkin.exception.AppException;
import com.leo.checkin.model.Student;
import com.leo.checkin.service.QuartzService;
import com.leo.checkin.service.StudentService;
import com.leo.checkin.util.Checkin;
import com.leo.checkin.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * @Description:
 * @Author: leo
 * @Date: 2021/9/16 22:02
 */
@Slf4j
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private QuartzService quartzService;

    @Override
    public StudentLoginOutputDTO login(StudentLoginInputDTO inputDTO) {
        // 根据学号密码查询
        QueryWrapper query = new QueryWrapper();
        query.eq("stu_num", inputDTO.getStuNum());
        query.eq("stu_pass", inputDTO.getStuPass());
        Student student = studentMapper.selectOne(query);
        if (student != null) {
            String token = TokenUtil.getToken(student.getStuNum());
            StudentLoginOutputDTO outputDTO = new StudentLoginOutputDTO();
            BeanUtils.copyProperties(student, outputDTO);
            outputDTO.setToken(token);
            return outputDTO;
        }
        // 数据库没有记录验证学号密码是否有效并入库
        Map<String, String> cookie;
        try {
            cookie = Checkin.login(inputDTO.getStuNum(), inputDTO.getStuPass());
        } catch (IOException e) {
            log.error("IOException:{}", e.getMessage());
            throw new AppException("登录失败");
        }
        // 入库
        student = new Student();
        BeanUtils.copyProperties(inputDTO, student);
        studentMapper.insert(student);

        // 返回stu
        String token = TokenUtil.getToken(student.getStuNum());
        StudentLoginOutputDTO outputDTO = new StudentLoginOutputDTO();
        BeanUtils.copyProperties(student, outputDTO);
        outputDTO.setToken(token);
        return outputDTO;
    }

    @Override
    public boolean setCheckinfo(CheckInfoUpdateInputDTO inputDTO, String token) {
        String username = TokenUtil.getUsernameFromToken(token);
        if (!inputDTO.getStuNum().equals(username)) {
            throw new AppException("你没有权限操作");
        }
        Student stu = new Student();
        BeanUtils.copyProperties(inputDTO, stu);
        if (studentMapper.updateCheckInfoById(stu) != 1) {
            return false;
        }
        quartzService.jobChange(inputDTO.getStuNum());
        return true;
    }
}
