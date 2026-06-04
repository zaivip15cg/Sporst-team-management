package com.example.sportsteammanagement.service;

import com.example.sportsteammanagement.Exception.AppException;
import com.example.sportsteammanagement.Exception.ErrorCode;
import com.example.sportsteammanagement.dto.request.AuthenticationRequest;
import com.example.sportsteammanagement.dto.request.IntrospectRequest;
import com.example.sportsteammanagement.dto.request.LogoutRequest;
import com.example.sportsteammanagement.dto.response.AuthenticationResponse;
import com.example.sportsteammanagement.dto.IntrospectResponse;
import com.example.sportsteammanagement.entity.Tokens;
import com.example.sportsteammanagement.entity.User;
import com.example.sportsteammanagement.repository.TokenRepository;
import com.example.sportsteammanagement.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    UserRepository userRepository;
    TokenRepository tokenRepository;
    @NonFinal
    protected static final String SIGINKEY = "7y6-jr;z1B?-RtObGN|:]-T!{v!+vPc$";
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(() ->
                new AppException(ErrorCode.User_NOT_Exist));


        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!authenticated)
            throw new AppException(ErrorCode.UnAuthenticated);

        var token = generateToken(user );
        tokenRepository.save(Tokens.builder()
                .token(token)
                .user(user)
                .expiresAt(LocalDateTime.now().plusHours(1))
                .build());
return AuthenticationResponse.builder().token(token).authenticated(true).build();



    }
    public void logout (LogoutRequest request) {
        Tokens tokens = tokenRepository.findByToken(request.getToken())
                .orElseThrow(() -> new AppException(ErrorCode.TOKEN_NOT_EXIST);
                
                

    }


    }

     private String generateToken(User user){
         JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
         JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                 .subject(user.getEmail())
                 .issuer("devteria.com")
                 .issueTime(new Date())
                 .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                 .claim("scope", buildScope(user))
                 .build();

         Payload payload = new Payload(jwtClaimsSet.toJSONObject());
         JWSObject jwsObject = new JWSObject(header, payload);
         try {
             jwsObject.sign(new MACSigner(SIGINKEY.getBytes()));
             return jwsObject.serialize();
         } catch (JOSEException e) {
             log.error("Khong the tao token", e);
             throw new RuntimeException(e);
         }


     }
     private String buildScope(User user){
         StringJoiner stringJoiner = new StringJoiner(" ");
         if (user.getRole() != null) {
             stringJoiner.add("ROLE_" + user.getRole().name());
         }
         return stringJoiner.toString();
     }
     public IntrospectResponse introspectResponse(IntrospectRequest request) throws JOSEException, ParseException {

            var token = request.getToken();

            JWSVerifier verifier = new MACVerifier((SIGINKEY.getBytes()));

            SignedJWT signedJWT = SignedJWT.parse(token);
            Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            var verified = signedJWT.verify(verifier);
         if (!verified)
             throw new AppException(ErrorCode.TOKEN_NOT_EXIST);

            return IntrospectResponse.builder()
                    .valid(verified && expiryTime.after(new Date())).build();






         }




     }
