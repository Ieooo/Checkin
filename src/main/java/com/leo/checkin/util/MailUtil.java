package com.leo.checkin.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Description:
 * @Author: leo
 * @Date: 2021/9/19 15:06
 */
@Slf4j
@Component
public class MailUtil {
    private static JavaMailSender javaMailSender;
    private static String from;
    @Value("${spring.mail.username}")
    private String f;
    @Autowired
    private JavaMailSender sender;

    @PostConstruct
    public void init() {
        javaMailSender = this.sender;
        from = this.f;
    }

    public static boolean sendMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setText(content);
        message.setSubject(subject);
        message.setTo(to);
        message.setFrom(from);
        try {
            javaMailSender.send(message);
        } catch (MailException e) {
            log.error("mail send error:{}",e.getMessage());
            return false;
        }
        return true;
    }
}
