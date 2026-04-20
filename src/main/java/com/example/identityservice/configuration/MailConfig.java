package com.example.identityservice.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import java.util.Properties;
import org.springframework.mail.MailSender;

@Configuration

public class MailConfig {
    @Value("${mailSever.host}")
    private String host;

    @Value("${mailSever.port}")
    private Integer port;

    @Value("${mailSever.mail_email}")
    private String mail_email;


    @Value(value = "${mailSever.mail_password}")
    private String mail_password;

    @Value("${mailSever.isSSL}")
    private String isSSL;

    @Bean
    public JavaMailSender getJavaMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);

        mailSender.setUsername(mail_email);
        mailSender.setPassword(mail_password);
        mailSender.setDefaultEncoding("UTF-8");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.enable", isSSL);
        props.put("mail.smtp.from", mail_email);
        props.put("mail.debug", "true");

        return mailSender;


    }

}

