package com.example.identityservice.controller;

import com.example.identityservice.Exception.AppException;
import com.example.identityservice.Exception.ErrorCode;
import com.example.identityservice.dto.APIResponse;
import com.example.identityservice.dto.request.AuthenticationRequest;
import com.example.identityservice.dto.request.IntrospectRequest;
import com.example.identityservice.dto.response.AuthenticationResponse;
import com.example.identityservice.dto.IntrospectResponse;
import com.example.identityservice.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {

    AuthenticationService authenticationService;
    @PostMapping("/token")
    APIResponse<AuthenticationResponse> authentication(@RequestBody AuthenticationRequest request){
      var result =  authenticationService.authenticate(request);
      return APIResponse.<AuthenticationResponse>
              builder().result(result).build();
    }

    @PostMapping("/introspect")
    APIResponse<IntrospectResponse> authentication(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {

            var result = authenticationService.introspectResponse(request);
            return APIResponse.<IntrospectResponse>
                    builder().result(result).build();
    }





}
