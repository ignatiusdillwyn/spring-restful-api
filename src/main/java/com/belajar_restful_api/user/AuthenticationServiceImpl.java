package com.belajar_restful_api.user;

import com.belajar_restful_api.config.JwtService;
import com.belajar_restful_api.dto.AuthenticationRequest;
import com.belajar_restful_api.dto.AuthenticationResponse;
import com.belajar_restful_api.dto.RegisterRequest;
import com.belajar_restful_api.roles.Roles;
import com.belajar_restful_api.roles.RolesRepository;
import com.belajar_restful_api.roles.RolesService;
import com.belajar_restful_api.user.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{

    private final UsersRepository usersRepository;
    private final RolesRepository rolesRepository;
    private final RolesService rolesService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final Long ROLE_USER = 1l;

    @Override
    public AuthenticationResponse authentication(AuthenticationRequest request) {

        //Pakai try catch biar pas bisa mapping error response di controllernya, bisa tambahin message error, dll
        try {
            //Proses autentikasi yang sesungguhnya, kalau ini gak ada ya loss aja, gak peduli email password bener atau salah
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            throw new RuntimeException("EMAIL_OR_PASSWORD_INVALID");
        }

        var user = usersRepository.findByEmail(request.getEmail())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }


    @Override
    public AuthenticationResponse register(RegisterRequest request) {

        Roles role = rolesRepository.findById(ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Role USER not found"));

        Users user = Users.builder()
                .email(request.getEmail())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(role) // ✅ entity managed
                .build();

        System.out.println("sampai sini aja");
        usersRepository.save(user);

        var jwtToken = jwtService.generateToken(user);
        System.out.println("jwt token " + jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}