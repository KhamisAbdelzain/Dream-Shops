package com.khamis.dreamshops.controller;

import com.khamis.dreamshops.request.LoginRequest;
import com.khamis.dreamshops.response.ApiResponse;
import com.khamis.dreamshops.response.JwtResponse;
import com.khamis.dreamshops.security.jwt.JwtUtils;
import com.khamis.dreamshops.security.user.ShopUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwt;


    @PostMapping("/login")
    public ResponseEntity<ApiResponse>loginRequest(@Valid @RequestBody LoginRequest loginRequest){
        try {
            Authentication authentication=authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),loginRequest.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwtToken=jwt.generateToken(authentication);
            ShopUserDetails userDetails=(ShopUserDetails) authentication.getPrincipal();
            JwtResponse jwtResponse=new JwtResponse(userDetails.getId(),jwtToken);
            return ResponseEntity.ok(new ApiResponse("Login Sucessfully",jwtResponse));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(e.getMessage(), null));
        }


    }

}
