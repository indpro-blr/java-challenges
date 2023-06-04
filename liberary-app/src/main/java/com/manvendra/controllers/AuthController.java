package com.manvendra.controllers;

import com.manvendra.dtos.UserRequest;
import com.manvendra.dtos.UserResponse;
import com.manvendra.models.User;
import com.manvendra.security.JwtUtil;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final ModelMapper modelMapper;

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody UserRequest userRequest) {


        doAuthenticate(userRequest.getUsername(), userRequest.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(userRequest.getUsername());
        String token = jwtUtil.generateToken(userDetails);
        UserResponse userResponse = new UserResponse(token, modelMapper.map(userDetails, User.class));
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    private void doAuthenticate(String username, String password) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password);
        try {
            authenticationManager.authenticate(authentication);
        } catch (BadCredentialsException ex) {
            ex.printStackTrace();
        }
    }
}
