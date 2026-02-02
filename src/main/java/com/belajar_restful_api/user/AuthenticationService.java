package com.belajar_restful_api.user;

import com.belajar_restful_api.dto.AuthenticationRequest;
import com.belajar_restful_api.dto.AuthenticationResponse;
import com.belajar_restful_api.dto.RegisterRequest;

public interface AuthenticationService {

    AuthenticationResponse authentication (AuthenticationRequest request);
    AuthenticationResponse register(RegisterRequest request);
}