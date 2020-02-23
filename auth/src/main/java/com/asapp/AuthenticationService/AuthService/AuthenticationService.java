package com.asapp.AuthenticationService.AuthService;


import java.util.Optional;

public interface AuthenticationService {

    public Optional<AuthResponse> authenticate(AuthReq authReq);

}
