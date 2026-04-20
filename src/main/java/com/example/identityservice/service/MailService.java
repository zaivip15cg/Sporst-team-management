package com.example.identityservice.service;

import com.example.identityservice.dto.mail.DataMailDTO;
import jakarta.mail.MessagingException;

public interface MailService {
    void sendHtmlMail(DataMailDTO dataMailDTO, String  templateName ) throws MessagingException;

}
