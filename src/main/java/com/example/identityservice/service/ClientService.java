package com.example.identityservice.service;

import com.example.identityservice.dto.mail.ClientSdi;

public interface ClientService {
    Boolean create(ClientSdi sdi);
}
