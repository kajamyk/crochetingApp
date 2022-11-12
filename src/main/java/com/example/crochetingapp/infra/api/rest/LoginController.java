package com.example.crochetingapp.infra.api.rest;

import com.example.crochetingapp.core.AuthRequest;
import com.example.crochetingapp.core.User;
import com.example.crochetingapp.infra.api.security.JwtUtil;
import com.example.crochetingapp.infra.api.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
public class LoginController {
    @Autowired
    UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping("/login")
    public String generateToken(@Valid @RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
            return jwtUtil.generateToken(authRequest.getUserName());
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }

        return null;
    }

    @PostMapping("/register")
    public String createAccount(@Valid @RequestBody AuthRequest authRequest) throws Exception {
        try {
            User user = new User(authRequest.getUserName(), authRequest.getPassword());
            userService.addUser(user);
            return generateToken(authRequest);
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }

        return null;
    }
}
