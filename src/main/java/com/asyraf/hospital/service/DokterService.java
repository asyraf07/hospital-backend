package com.asyraf.hospital.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asyraf.hospital.dto.DokterRequest;
import com.asyraf.hospital.dto.DokterResponse;
import com.asyraf.hospital.entity.Dokter;
import com.asyraf.hospital.entity.User;
import com.asyraf.hospital.repository.DokterRepository;
import com.asyraf.hospital.repository.UserRepository;

import lombok.SneakyThrows;

@Service
public class DokterService {

    private final DokterRepository dokterRepository;
    private final UserRepository userRepository;

    @Autowired
    public DokterService(DokterRepository dokterRepository, UserRepository userRepository) {
        this.dokterRepository = dokterRepository;
        this.userRepository = userRepository;
    }

    public List<String> getAllSpesialisasi() {
        return dokterRepository.findAllSpesialisasi();
    }

    public List<DokterResponse> getDokterBySpesialisasi(String spesialisasi) {
        List<DokterResponse> response = new ArrayList<>();

        List<Dokter> dokterList = dokterRepository.findBySpesialisasiIgnoreCase(spesialisasi);
        for (Dokter dokter : dokterList) {
            response.add(DokterResponse.builder()
                    .id(dokter.getId())
                    .nama(dokter.getUser().getNama())
                    .noHp(dokter.getUser().getNoHP())
                    .spesialisasi(dokter.getSpesialisasi())
                    .userId(dokter.getUser().getId())
                    .build());
        }
        return response;
    }

    public List<DokterResponse> getAllDokter() {
        List<DokterResponse> response = new ArrayList<>();

        List<Dokter> dokterList = dokterRepository.findAll();
        for (Dokter dokter : dokterList) {
            response.add(DokterResponse.builder()
                    .id(dokter.getId())
                    .nama(dokter.getUser().getNama())
                    .noHp(dokter.getUser().getNoHP())
                    .spesialisasi(dokter.getSpesialisasi())
                    .userId(dokter.getUser().getId())
                    .build());
        }
        return response;
    }

    @SneakyThrows
    public DokterResponse getDokter(Integer id) {
        Optional<Dokter> find = dokterRepository.findById(id);
        if (!find.isPresent()) {
            throw new ServiceException("Tidak ada dokter dengan ID ini!");
        }
        Dokter dokter = find.get();
        return DokterResponse.builder()
                .id(dokter.getId())
                .nama(dokter.getUser().getNama())
                .noHp(dokter.getUser().getNoHP())
                .spesialisasi(dokter.getSpesialisasi())
                .userId(dokter.getUser().getId())
                .build();
    }

    @SneakyThrows
    public DokterResponse addDokter(DokterRequest request) {
        Optional<User> find = userRepository.findById(request.getUserId());
        if (!find.isPresent()) {
            throw new ServiceException("Tidak ada user dengan ID ini!");
        }
        User user = find.get();
        Dokter dokter = new Dokter();
        dokter.setUser(user);
        dokter.setSpesialisasi(request.getSpesialisasi());
        dokterRepository.save(dokter);
        return DokterResponse.builder()
                .id(dokter.getId())
                .nama(dokter.getUser().getNama())
                .noHp(dokter.getUser().getNoHP())
                .spesialisasi(dokter.getSpesialisasi())
                .userId(dokter.getUser().getId())
                .build();
    }

    @SneakyThrows
    public DokterResponse updateDokter(Integer id, DokterRequest request) {
        Optional<Dokter> findDokter = dokterRepository.findById(id);
        if (!findDokter.isPresent()) {
            throw new ServiceException("Tidak ada dokter dengan ID ini!");
        }
        Optional<User> findUser = userRepository.findById(request.getUserId());
        if (!findUser.isPresent()) {
            throw new ServiceException("Tidak ada user dengan ID ini!");
        }
        User user = findUser.get();
        Dokter dokter = findDokter.get();
        dokter.setUser(user);
        dokter.setSpesialisasi(request.getSpesialisasi());
        dokterRepository.save(dokter);
        return DokterResponse.builder()
                .id(dokter.getId())
                .nama(dokter.getUser().getNama())
                .noHp(dokter.getUser().getNoHP())
                .spesialisasi(dokter.getSpesialisasi())
                .userId(dokter.getUser().getId())
                .build();
    }

    public String deleteDokter(Integer id) {
        Optional<Dokter> findDokter = dokterRepository.findById(id);
        if (!findDokter.isPresent()) {
            return String.format("Tidak ada dokter dengan id %s", id);
        }
        dokterRepository.delete(findDokter.get());
        return String.format("Dokter dengan id %s berhasil dihapus!", id);
    }

}
