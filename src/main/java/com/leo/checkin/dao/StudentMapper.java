package com.leo.checkin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leo.checkin.model.Student;

/**
 * @Description:
 * @Author: leo
 * @Date: 2021/9/16 21:52
 */
public interface StudentMapper extends BaseMapper<Student> {
    int updateCheckInfoById(Student student);
    int updateEnable(Student student);
}
