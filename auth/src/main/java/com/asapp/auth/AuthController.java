package com.asapp.auth;

import java.util.HashMap;
import java.util.Optional;

import com.asapp.AuthenticationService.AuthService.AuthReq;
import com.asapp.AuthenticationService.AuthService.AuthResponse;
import com.asapp.AuthenticationService.AuthService.LoginRequest;
import com.asapp.AuthenticationService.AuthService.LoginResponse;
import com.asapp.AuthenticationService.AuthSvc.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController

public class AuthController {

    AuthManager authManager;

    @Autowired
    public AuthController(AuthManager authManager){
        this.authManager = authManager;
    }

    @PostMapping("/users")
    public HashMap<String, String> signUp(@RequestBody AuthReq authReq) {
       Optional<AuthResponse> authResponse = authManager.authenticate(authReq);
       HashMap<String,String> response =  new HashMap<>();
        if(authResponse.isPresent()){
            if(authResponse.get().getErrorResponse() == null){
                response.put("id", authResponse.get().getUsername());
                return response;
            } else {
                response.put("error", authResponse.get().getErrorResponse().getError());
                return response;
            }
        }
        response.put("error", ErrorResponse.UNABLE_TO_SIGN_UP.getError());
        return response;
    }
    @PostMapping("/login")
    public HashMap<String,String> login(@RequestBody LoginRequest loginRequest) {
        Optional<LoginResponse> loginResponse = authManager.login(loginRequest);
        HashMap<String, String> response = new HashMap<>();
        if(loginResponse.isPresent()){
            if(loginResponse.get().getAuthToken()!=null && loginResponse.get().getErrorResponse() == null) {
                response.put(String.valueOf(loginResponse.get().getUserId()),loginResponse.get().getAuthToken());
                return response;
            }
            response.put("Error",loginResponse.get().getErrorResponse().getError());
            return response;
        }
        response.put("Error", ErrorResponse.UNABLE_TO_LOGIN.getError());
        return response;
    }

    @RequestMapping(value ="/checkAuthToken/{authToken}", method = {RequestMethod.GET})
    public boolean checkAuthToken(@PathVariable String authToken){
        if(authToken == null || authToken.length() ==0) {
            return false;
        }
        return authManager.isValidToken(authToken.split(" +")[1]);

    }

}
