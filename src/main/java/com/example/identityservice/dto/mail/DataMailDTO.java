package com.example.identityservice.dto.mail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class DataMailDTO {
    private String To;
    private String Subject;
    private String Content;
    private Map<String, Object> Props;
}
