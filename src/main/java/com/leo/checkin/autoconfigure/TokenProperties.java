package com.leo.checkin.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: leo
 * @Date: 2021/9/15 22:47
 */
@Component
@ConfigurationProperties(prefix = "application.token")
@Data
public class TokenProperties {
    private String secret;

    private Integer expire;
}
