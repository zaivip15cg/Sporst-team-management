package com.example.identityservice.service.impl;

import com.example.identityservice.dto.mail.DataMailDTO;
import com.example.identityservice.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

public class MailServiceImpl implements MailService {
    @Autowired
    JavaMailSender mailSender;
    @Autowired
    SpringTemplateEngine templateEngine;
    @Override
    public  void sendHtmlMail(DataMailDTO dataMailDTO, String templateName) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");

        Context context = new Context();
        context.setVariables(dataMailDTO.getProps());

        String html = templateEngine.process(templateName, context);

        helper.setTo(dataMailDTO.getTo());
        helper.setSubject(dataMailDTO.getSubject());
        helper.setText(html, true);

        mailSender.send(message);



    }
}
