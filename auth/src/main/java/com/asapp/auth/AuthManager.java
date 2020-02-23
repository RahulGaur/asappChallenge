package com.asapp.auth;

import java.util.Optional;

import com.asapp.AuthenticationService.AuthService.AuthReq;
import com.asapp.AuthenticationService.AuthService.AuthResponse;
import com.asapp.AuthenticationService.AuthService.LoginRequest;
import com.asapp.AuthenticationService.AuthService.LoginResponse;
import com.asapp.AuthenticationService.AuthSvc.AuthDAO;
import com.asapp.AuthenticationService.AuthSvc.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
//import com.asapp.AuthenticationService.AuthService.AuthenticationService;
//import org.springframework.stereotype.Service;

@SpringBootApplication
@ComponentScan("com.asapp")
@EntityScan("com.asapp.auth")
@Service
@Repository
public class AuthManager {

    AuthDAO authDAO;

    @Autowired
    public AuthManager(AuthDAO authDAO) {
        this.authDAO = authDAO;
    }
    public Optional<AuthResponse> authenticate(AuthReq authReq) {
        if (authReq == null || authReq.getUsername() == null || authReq.getPassword() == null)
            return Optional.empty();
        else {
            Optional<AuthResponse> response = authDAO.createUser(authReq.getUsername(),authReq.getPassword());
            if (!response.isPresent()) {
                response.get().setErrorResponse(ErrorResponse.UNABLE_TO_SIGN_UP);
            }
            return response;
        }

    }

    public Optional<LoginResponse> login(LoginRequest loginRequest) {
        if(loginRequest == null || loginRequest.getUsername() == null || loginRequest.getPassword() == null) {
            LoginResponse loginResponse = new LoginResponse(ErrorResponse.INCORRECT_REQUEST);
            return Optional.of(loginResponse);
        }
        return authDAO.login(loginRequest.getUsername(), loginRequest.getPassword());
    }

    public boolean isValidToken(String authToken) {
        if (authToken.split(" ").length==2) {
            authToken = authToken.split(" +")[1];
        }
        return authDAO.isValidToken(authToken);

    }
}
