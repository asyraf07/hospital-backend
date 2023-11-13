package com.asyraf.hospital.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asyraf.hospital.dto.LoginRequest;
import com.asyraf.hospital.dto.LoginResponse;
import com.asyraf.hospital.dto.RegisterRequest;
import com.asyraf.hospital.dto.RegisterResponse;
import com.asyraf.hospital.entity.User;
import com.asyraf.hospital.repository.UserRepository;
import com.asyraf.hospital.util.TokenGenerator;

import lombok.SneakyThrows;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public RegisterResponse register(RegisterRequest request) {
        User user = new User();
        user.setNama(request.getNama());
        user.setNoHP(request.getNoHp());
        userRepository.save(user);
        return RegisterResponse.builder()
                .id(user.getId())
                .nama(user.getNama())
                .noHp(user.getNoHP())
                .build();
    }

    @SneakyThrows
    public LoginResponse login(LoginRequest request) {
        Optional<User> find = userRepository.findByNamaAndNoHP(request.getNama(), request.getNoHp());
        if (!find.isPresent()) {
            throw new Exception();
        }
        User user = find.get();
        user.setToken(TokenGenerator.generate());
        userRepository.save(user);
        return LoginResponse.builder()
                .token(user.getToken())
                .build();
    }

}
