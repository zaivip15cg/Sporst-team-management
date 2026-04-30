package com.example.sportsteammanagement.controller;

import com.example.sportsteammanagement.Exception.AppException;
import com.example.sportsteammanagement.Exception.ErrorCode;
import com.example.sportsteammanagement.dto.APIResponse;
import com.example.sportsteammanagement.dto.request.AuthenticationRequest;
import com.example.sportsteammanagement.dto.request.IntrospectRequest;
import com.example.sportsteammanagement.dto.response.AuthenticationResponse;
import com.example.sportsteammanagement.dto.IntrospectResponse;
import com.example.sportsteammanagement.service.AuthenticationService;
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
    APIResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {

            var result = authenticationService.introspectResponse(request);
            return APIResponse.<IntrospectResponse>
                    builder().result(result).build();
    }





}
