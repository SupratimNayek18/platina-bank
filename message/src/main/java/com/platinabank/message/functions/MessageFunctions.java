package com.platinabank.message.functions;

import com.platinabank.message.dto.AccountsMsgDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.function.Function;

@Configuration
public class MessageFunctions {

    @Autowired
    private JavaMailSender mailSender;

    private static final Logger logger = LoggerFactory.getLogger(MessageFunctions.class);

    @Bean
    public Function<AccountsMsgDto, AccountsMsgDto> email() {
        return accountsMsgDto -> {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("platinabank.helpline@gmail.com");
            message.setTo(accountsMsgDto.email());
            message.setSubject("Welcome to Platina Bank");
            message.setText("Your account number is : " + accountsMsgDto.accountNumber() + "/n We welcome you to a safe and secure banking experience with Platina Bank");
            mailSender.send(message);
            logger.info("Sending email with the details :" + accountsMsgDto.toString());
            return accountsMsgDto;
        };
    }

    @Bean
    public Function<AccountsMsgDto, Long> sms() {
        return accountsMsgDto -> {
            logger.info("Sending sms with the details :" + accountsMsgDto.toString());
            return accountsMsgDto.accountNumber();
        };
    }
}
