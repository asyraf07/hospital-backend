package com.asyraf.hospital.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asyraf.hospital.dto.LoginRequest;
import com.asyraf.hospital.dto.LoginResponse;
import com.asyraf.hospital.dto.RegisterRequest;
import com.asyraf.hospital.dto.RegisterResponse;
import com.asyraf.hospital.entity.Dokter;
import com.asyraf.hospital.entity.Pasien;
import com.asyraf.hospital.entity.User;
import com.asyraf.hospital.repository.DokterRepository;
import com.asyraf.hospital.repository.PasienRepository;
import com.asyraf.hospital.repository.UserRepository;
import com.asyraf.hospital.util.TokenGenerator;

import lombok.SneakyThrows;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasienRepository pasienRepository;
    private final DokterRepository dokterRepository;

    @Autowired
    public AuthService(UserRepository userRepository, PasienRepository pasienRepository, DokterRepository dokterRepository) {
        this.userRepository = userRepository;
        this.pasienRepository = pasienRepository;
        this.dokterRepository = dokterRepository;
    }

    public RegisterResponse registerPasien(RegisterRequest request) {
        User user = new User();
        user.setNama(request.getNama());
        user.setNoHP(request.getNoHp());

        User savedUser = userRepository.save(user);
        Pasien pasien = new Pasien();
        pasien.setUser(savedUser);
        pasienRepository.save(pasien);

        return RegisterResponse.builder()
                .id(savedUser.getId())
                .nama(savedUser.getNama())
                .noHp(savedUser.getNoHP())
                .build();
    }

    public RegisterResponse registerDokter(RegisterRequest request) {
        User user = new User();
        user.setNama(request.getNama());
        user.setNoHP(request.getNoHp());

        User savedUser = userRepository.save(user);
        Dokter dokter = new Dokter();
        dokter.setUser(savedUser);
        dokter.setSpesialisasi(request.getSpesialis());
        dokterRepository.save(dokter);

        return RegisterResponse.builder()
                .id(savedUser.getId())
                .nama(savedUser.getNama())
                .noHp(savedUser.getNoHP())
                .build();
    }

    @SneakyThrows
    public LoginResponse login(LoginRequest request) {
        Optional<User> find = userRepository.findByNamaAndNoHP(request.getNama(), request.getNoHp());
        if (!find.isPresent()) {
            return LoginResponse.builder()
                    .build();
        }
        User user = find.get();
        user.setToken(TokenGenerator.generate());
        userRepository.save(user);
        Optional<Pasien> pasien = pasienRepository.findByUserId(user.getId());
        Integer pasienId = 1;
        if (pasien.isPresent()) {
            pasienId = pasien.get().getId();
        }

        return LoginResponse.builder()
                .userId(user.getId())
                .pasienId(pasienId)
                .token(user.getToken())
                .build();
    }

}
