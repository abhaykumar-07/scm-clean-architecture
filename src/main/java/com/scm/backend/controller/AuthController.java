package com.scm.backend.controller;

import com.scm.backend.model.JwtResponse;
import com.scm.backend.model.LoginRequest;
import com.scm.backend.model.ErrorResponse;
import com.scm.backend.payload.RegisterRequest;
import com.scm.backend.security.jwt.JWTUtil;
import com.scm.backend.security.CustomUserDetailsService;
import com.scm.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public <UserDto> ResponseEntity<UserDto> registerUser(@Valid @RequestBody RegisterRequest request) {
        UserDto registeredUser = (UserDto) userService.registerNewUser(request);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
            String token = jwtUtil.generateToken(userDetails);

            JwtResponse jwtResponse = new JwtResponse(token, request.getEmail());
            return ResponseEntity.ok(jwtResponse);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Unauthorized", "Invalid email or password"));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Internal Server Error", ex.getMessage()));
        }
    }
}
