package com.example.sportsteammanagement.service;

import com.example.sportsteammanagement.dto.mail.DataMailDTO;
import jakarta.mail.MessagingException;

public interface MailService {
    void sendHtmlMail(DataMailDTO dataMailDTO, String  templateName ) throws MessagingException;

}
