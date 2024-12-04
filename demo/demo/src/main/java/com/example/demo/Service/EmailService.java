package com.example.demo.Service;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {

    @Resource
    private JavaMailSender javaMailSender;
    @Resource
    private RedisService redisService=new RedisService();

    public boolean sendEmail(String to, String subject, String body) {
        if(redisService.getValue(to)!=null) return false;
        SimpleMailMessage message = new SimpleMailMessage();
        //设置发送对象
        message.setTo(to);
        //设置邮件标题
        message.setSubject(subject);
        //设置邮件内容
        message.setText(body);
        //邮件由该邮箱发送
        message.setFrom("204299687@qq.com");
        javaMailSender.send(message);
        log.info("向"+to+"发送邮件");
        return true;
    }
}
