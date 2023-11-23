package com.asyraf.hospital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.asyraf.hospital.dto.LoginRequest;
import com.asyraf.hospital.dto.LoginResponse;
import com.asyraf.hospital.dto.RegisterRequest;
import com.asyraf.hospital.dto.RegisterResponse;
import com.asyraf.hospital.service.AuthService;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register/pasien")
    public ResponseEntity<RegisterResponse> registerPasien(@RequestBody RegisterRequest request) {
        return new ResponseEntity<>(authService.registerPasien(request), HttpStatus.CREATED);
    }

    @PostMapping("/register/dokter")
    public ResponseEntity<RegisterResponse> registerDokter (@RequestBody RegisterRequest request) {
        return new ResponseEntity<>(authService.registerDokter(request), HttpStatus.CREATED);
    }

}
