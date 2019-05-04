package com.nevergetme.nevergetmeweb.service.impl;


import com.nevergetme.nevergetmeweb.config.StaticConfigParam;
import com.nevergetme.nevergetmeweb.service.SendEmailService;
import com.nevergetme.nevergetmeweb.utility.ContentUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class SendEmailServiceImpl implements SendEmailService {
    @Value("${spring.mail.username}")
    private String whoAmI;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
     /**
     　　* @description: TODO
     　　* @param [to, subject, content] 
     　　* @return void
     　　* @throws 
     　　* @author Alden He
     　　* @date 2019/5/3 21:16 
     　　*/
    public void sendSimpleEmail(String to, String subject, String content) {
        SimpleMailMessage message=new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        message.setFrom(whoAmI);
        mailSender.send(message);
    }
    @Override
     /**
     　　* @description: TODO
     　　* @param [to]
     　　* @return boolean
     　　* @throws 
     　　* @author Alden He
     　　* @date 2019/5/3 21:19
     　　*/
    public boolean sendRegisterEmail(String to) {
        String key=StaticConfigParam.REGISTER_EMAIL_CODE_REDIS+to;
        if(!redisTemplate.hasKey(key)){
            SimpleMailMessage message=new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(StaticConfigParam.REGISTER_SEND_EMAIL_SUBJECT);
            int authCode=ContentUtility.getRandomInteger();
            message.setText(StaticConfigParam.REGISTER_SEND_EMAIL_CONTENT+ authCode);
            message.setFrom(whoAmI);
            mailSender.send(message);
            redisTemplate.opsForValue().set(key,authCode);
            return true;
        }else{
            return false;
        }
    }

    /**
     * 获取存储在redis中的验证码
     * @param to
     * @return
     */
    @Override
    public int getRegisterAuthCode(String to) {
        String key=StaticConfigParam.REGISTER_EMAIL_CODE_REDIS+to;
        if(redisTemplate.hasKey(key)){
            return (Integer) redisTemplate.opsForValue().get(key);
        }
            return 0;
    }

    /**
     * 删除存储在redis中的验证码
     * @param to
     * @return
     */
    @Override
    public boolean deleteRegisterAuthCode(String to) {
        String key=StaticConfigParam.REGISTER_EMAIL_CODE_REDIS+to;
        if(redisTemplate.hasKey(key)){
            redisTemplate.delete(key);
        }
        return true;
    }

    @Override
    public void sendHtmlEmail(String to, String subject, String content) {

    }
}
