package com.asyraf.hospital.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asyraf.hospital.dto.PasienRequest;
import com.asyraf.hospital.dto.PasienResponse;
import com.asyraf.hospital.entity.Pasien;
import com.asyraf.hospital.entity.User;
import com.asyraf.hospital.repository.PasienRepository;
import com.asyraf.hospital.repository.UserRepository;

import lombok.SneakyThrows;

@Service
public class PasienService {

    @Autowired
    PasienRepository pasienRepository;

    @Autowired
    UserRepository userRepository;

    public List<PasienResponse> getAllPasien() {
        List<PasienResponse> response = new ArrayList<>();

        List<Pasien> pasienList = pasienRepository.findAll();
        for (Pasien pasien : pasienList) {
            response.add(PasienResponse.builder()
                    .id(pasien.getId())
                    .nama(pasien.getUser().getNama())
                    .noHp(pasien.getUser().getNoHP())
                    .userId(pasien.getUser().getId())
                    .build());
        }
        return response;
    }

    @SneakyThrows
    public PasienResponse getPasien(Integer id) {
        Optional<Pasien> find = pasienRepository.findById(id);
        if (!find.isPresent()) {
            throw new Exception();
        }
        Pasien pasien = find.get();
        return PasienResponse.builder()
                .id(pasien.getId())
                .nama(pasien.getUser().getNama())
                .noHp(pasien.getUser().getNoHP())
                .userId(pasien.getUser().getId())
                .build();
    }

    @SneakyThrows
    public PasienResponse addPasien(PasienRequest request) {
        Optional<User> find = userRepository.findById(request.getUserId());
        if (!find.isPresent()) {
            throw new Exception();
        }
        User user = find.get();
        Pasien pasien = new Pasien();
        pasien.setUser(user);
        pasienRepository.save(pasien);
        return PasienResponse.builder()
                .id(pasien.getId())
                .nama(pasien.getUser().getNama())
                .noHp(pasien.getUser().getNoHP())
                .userId(pasien.getUser().getId())
                .build();
    }

    @SneakyThrows
    public PasienResponse updatePasien(Integer id, PasienRequest request) {
        Optional<Pasien> findPasien = pasienRepository.findById(id);
        if (!findPasien.isPresent()) {
            throw new Exception();
        }
        Optional<User> findUser = userRepository.findById(request.getUserId());
        if (!findUser.isPresent()) {
            throw new Exception();
        }
        User user = findUser.get();
        Pasien pasien = findPasien.get();
        pasien.setUser(user);
        pasienRepository.save(pasien);
        return PasienResponse.builder()
                .id(pasien.getId())
                .nama(pasien.getUser().getNama())
                .noHp(pasien.getUser().getNoHP())
                .userId(pasien.getUser().getId())
                .build();
    }

    public String deletePasien(Integer id) {
        Optional<Pasien> findPasien = pasienRepository.findById(id);
        if (!findPasien.isPresent()) {
            return String.format("Tidak ada pasien dengan id %s", id);
        }
        pasienRepository.delete(findPasien.get());
        return String.format("Pasien dengan id %s berhasil dihapus!", id);
    }

}
