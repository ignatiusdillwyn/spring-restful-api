package com.belajar_restful_api.controllers;

import com.belajar_restful_api.dto.AuthenticationRequest;
import com.belajar_restful_api.dto.AuthenticationResponse;
import com.belajar_restful_api.dto.RegisterRequest;
import com.belajar_restful_api.user.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping(value = "/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.status(HttpStatus.OK)
                .body(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) {
        try {
            AuthenticationResponse response = authenticationService.authentication(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            if ("EMAIL_OR_PASSWORD_INVALID".equals(e.getMessage())) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of(
                                "status", 401,
                                "message", "Email atau password salah"
                        ));
            }
            throw e;
        }
    }
}
