package com.leo.checkin.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: leo
 * @Date: 2021/9/19 0:06
 */
@Component
@ConfigurationProperties(prefix = "application.swagger")
@Data
public class SwaggerProperties {
    private String title;
    private String description;
    private String version;
    private String groupName;
}
