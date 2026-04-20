package com.example.identityservice.service.impl;

import com.example.identityservice.dto.mail.ClientSdi;
import com.example.identityservice.service.ClientService;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {
    @Override
    public Boolean create(ClientSdi sdi){
        //xử lý các nghiệp vụ trước khi tạo thông tin người dùng
        return null;

    }

}
