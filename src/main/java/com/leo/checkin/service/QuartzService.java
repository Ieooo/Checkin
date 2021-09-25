package com.leo.checkin.service;

import com.leo.checkin.dto.TaskEnableInputDTO;

/**
 * @Description:
 * @Author: leo
 * @Date: 2021/9/14 23:34
 */
public interface QuartzService {
    /**
     *
     * @param username
     */
    void jobChange(String username);

    /**
     *
     */
    void startAllJobs();

    /**
     *
     * @param inputDTO
     */
    void enableTask(TaskEnableInputDTO inputDTO, String token);

}
