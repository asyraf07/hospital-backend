package com.asyraf.hospital.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asyraf.hospital.dto.PengobatanRequest;
import com.asyraf.hospital.dto.PengobatanResponse;
import com.asyraf.hospital.entity.Dokter;
import com.asyraf.hospital.entity.Pasien;
import com.asyraf.hospital.entity.Pengobatan;
import com.asyraf.hospital.repository.DokterRepository;
import com.asyraf.hospital.repository.PasienRepository;
import com.asyraf.hospital.repository.PengobatanRepository;

import lombok.SneakyThrows;

@Service
public class PengobatanService {

    @Autowired
    PengobatanRepository pengobatanRepository;

    @Autowired
    PasienRepository pasienRepository;

    @Autowired
    DokterRepository dokterRepository;

    public List<PengobatanResponse> getAllPengobatan() {
        List<PengobatanResponse> response = new ArrayList<>();

        List<Pengobatan> pengobatanList = pengobatanRepository.findAll();
        for (Pengobatan pengobatan : pengobatanList) {
            response.add(PengobatanResponse.builder()
                    .id(pengobatan.getId())
                    .namaPasien(pengobatan.getPasien().getUser().getNama())
                    .namaDokter(pengobatan.getDokter().getUser().getNama())
                    .penyakit(pengobatan.getPenyakit())
                    .tanggalPengobatan(pengobatan.getTanggalPengobatan())
                    .build());
        }
        return response;
    }

    @SneakyThrows
    public PengobatanResponse getPengobatan(Integer id) {
        Optional<Pengobatan> find = pengobatanRepository.findById(id);
        if (!find.isPresent()) {
            throw new Exception();
        }
        Pengobatan pengobatan = find.get();
        return PengobatanResponse.builder()
                .id(pengobatan.getId())
                .namaPasien(pengobatan.getPasien().getUser().getNama())
                .namaDokter(pengobatan.getDokter().getUser().getNama())
                .penyakit(pengobatan.getPenyakit())
                .tanggalPengobatan(pengobatan.getTanggalPengobatan())
                .build();
    }

    @SneakyThrows
    public PengobatanResponse addPengobatan(PengobatanRequest request) {
        Optional<Pasien> findPasien = pasienRepository.findById(request.getPasienId());
        Optional<Dokter> findDokter = dokterRepository.findById(request.getDokterId());
        if (!findPasien.isPresent() || !findDokter.isPresent()) {
            throw new Exception();
        }
        Pasien pasien = findPasien.get();
        Dokter dokter = findDokter.get();
        Pengobatan pengobatan = new Pengobatan();
        pengobatan.setPasien(pasien);
        pengobatan.setDokter(dokter);
        pengobatan.setPenyakit(request.getPenyakit());
        pengobatan.setTanggalPengobatan(new Date());
        pengobatanRepository.save(pengobatan);
        return PengobatanResponse.builder()
                .id(pengobatan.getId())
                .namaPasien(pengobatan.getPasien().getUser().getNama())
                .namaDokter(pengobatan.getDokter().getUser().getNama())
                .penyakit(pengobatan.getPenyakit())
                .tanggalPengobatan(pengobatan.getTanggalPengobatan())
                .build();
    }

    @SneakyThrows
    public PengobatanResponse updatePengobatan(Integer id, PengobatanRequest request) {
        Optional<Pengobatan> findPengobatan = pengobatanRepository.findById(id);
        if (!findPengobatan.isPresent()) {
            throw new Exception();
        }
        Optional<Pasien> findPasien = pasienRepository.findById(request.getPasienId());
        Optional<Dokter> findDokter = dokterRepository.findById(request.getDokterId());
        if (!findPasien.isPresent() || !findDokter.isPresent()) {
            throw new Exception();
        }
        Pasien pasien = findPasien.get();
        Dokter dokter = findDokter.get();
        Pengobatan pengobatan = findPengobatan.get();
        pengobatan.setPasien(pasien);
        pengobatan.setDokter(dokter);
        pengobatan.setPenyakit(request.getPenyakit());
        pengobatan.setTanggalPengobatan(new Date());
        pengobatanRepository.save(pengobatan);
        return PengobatanResponse.builder()
                .id(pengobatan.getId())
                .namaPasien(pengobatan.getPasien().getUser().getNama())
                .namaDokter(pengobatan.getDokter().getUser().getNama())
                .penyakit(pengobatan.getPenyakit())
                .tanggalPengobatan(pengobatan.getTanggalPengobatan())
                .build();
    }

    public String deletePengobatan(Integer id) {
        Optional<Pengobatan> findPengobatan = pengobatanRepository.findById(id);
        if (!findPengobatan.isPresent()) {
            return String.format("Tidak ada pengobatan dengan id %s", id);
        }
        pengobatanRepository.delete(findPengobatan.get());
        return String.format("Pengobatan dengan id %s berhasil dihapus!", id);
    }

}
