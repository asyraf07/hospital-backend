package com.asyraf.hospital.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asyraf.hospital.dto.KamarPasienRequest;
import com.asyraf.hospital.dto.KamarPasienResponse;
import com.asyraf.hospital.entity.Kamar;
import com.asyraf.hospital.entity.Pasien;
import com.asyraf.hospital.entity.KamarPasien;
import com.asyraf.hospital.repository.KamarRepository;
import com.asyraf.hospital.repository.PasienRepository;
import com.asyraf.hospital.repository.KamarPasienRepository;

import lombok.SneakyThrows;

@Service
public class KamarPasienService {

    public final KamarPasienRepository kamarPasienRepository;
    public final PasienRepository pasienRepository;
    public final KamarRepository kamarRepository;

    @Autowired
    public KamarPasienService(KamarPasienRepository kamarPasienRepository, PasienRepository pasienRepository, KamarRepository kamarRepository) {
        this.kamarPasienRepository = kamarPasienRepository;
        this.pasienRepository = pasienRepository;
        this.kamarRepository = kamarRepository;
    }

    public List<KamarPasienResponse> getAllKamarPasien() {
        List<KamarPasienResponse> response = new ArrayList<>();

        List<KamarPasien> kamarPasienList = kamarPasienRepository.findAll();
        for (KamarPasien kamarPasien : kamarPasienList) {
            response.add(KamarPasienResponse.builder()
                    .id(kamarPasien.getId())
                    .namaPasien(kamarPasien.getPasien().getUser().getNama())
                    .noKamar(kamarPasien.getKamar().getNoKamar())
                    .tanggalMasuk(kamarPasien.getTanggalMasuk())
                    .tanggalKeluar(kamarPasien.getTanggalKeluar())
                    .build());
        }
        return response;
    }

    @SneakyThrows
    public KamarPasienResponse getKamarPasien(Integer id) {
        Optional<KamarPasien> find = kamarPasienRepository.findById(id);
        if (!find.isPresent()) {
            throw new ServiceException("Tidak ada kamar pasien dengan ID ini!");
        }
        KamarPasien kamarPasien = find.get();
        return KamarPasienResponse.builder()
                .id(kamarPasien.getId())
                .namaPasien(kamarPasien.getPasien().getUser().getNama())
                .noKamar(kamarPasien.getKamar().getNoKamar())
                .tanggalMasuk(kamarPasien.getTanggalMasuk())
                .tanggalKeluar(kamarPasien.getTanggalKeluar())
                .build();
    }

    @SneakyThrows
    public KamarPasienResponse addKamarPasien(KamarPasienRequest request) {
        Optional<Pasien> findPasien = pasienRepository.findById(request.getPasienId());
        Optional<Kamar> findKamar = kamarRepository.findById(request.getKamarId());
        if (!findPasien.isPresent() || !findKamar.isPresent()) {
            throw new ServiceException("Tidak ada pasien dengan ID ini!");
        }
        Pasien pasien = findPasien.get();
        Kamar kamar = findKamar.get();
        KamarPasien kamarPasien = new KamarPasien();
        kamarPasien.setPasien(pasien);
        kamarPasien.setKamar(kamar);
        kamarPasien.setTanggalMasuk(request.getTanggalMasuk());
        kamarPasien.setTanggalKeluar(request.getTanggalKeluar());
        kamarPasienRepository.save(kamarPasien);
        return KamarPasienResponse.builder()
                .id(kamarPasien.getId())
                .namaPasien(kamarPasien.getPasien().getUser().getNama())
                .noKamar(kamarPasien.getKamar().getNoKamar())
                .tanggalMasuk(kamarPasien.getTanggalMasuk())
                .tanggalKeluar(kamarPasien.getTanggalKeluar())
                .build();
    }

    @SneakyThrows
    public KamarPasienResponse updateKamarPasien(Integer id, KamarPasienRequest request) {
        Optional<KamarPasien> findKamarPasien = kamarPasienRepository.findById(id);
        if (!findKamarPasien.isPresent()) {
            throw new ServiceException("Tidak ada kamar pasien dengan ID ini!");
        }
        Optional<Pasien> findPasien = pasienRepository.findById(request.getPasienId());
        Optional<Kamar> findKamar = kamarRepository.findById(request.getKamarId());
        if (!findPasien.isPresent() || !findKamar.isPresent()) {
            throw new ServiceException("Tidak ada kamar atau pasien dengan ID ini!");
        }
        Pasien pasien = findPasien.get();
        Kamar kamar = findKamar.get();
        KamarPasien kamarPasien = findKamarPasien.get();
        kamarPasien.setPasien(pasien);
        kamarPasien.setKamar(kamar);
        kamarPasien.setTanggalMasuk(request.getTanggalMasuk());
        kamarPasien.setTanggalKeluar(request.getTanggalKeluar());
        kamarPasienRepository.save(kamarPasien);
        return KamarPasienResponse.builder()
                .id(kamarPasien.getId())
                .namaPasien(kamarPasien.getPasien().getUser().getNama())
                .noKamar(kamarPasien.getKamar().getNoKamar())
                .tanggalMasuk(kamarPasien.getTanggalMasuk())
                .tanggalKeluar(kamarPasien.getTanggalKeluar())
                .build();
    }

    public String deleteKamarPasien(Integer id) {
        Optional<KamarPasien> findKamarPasien = kamarPasienRepository.findById(id);
        if (!findKamarPasien.isPresent()) {
            return String.format("Tidak ada kamar pasien dengan id %s", id);
        }
        kamarPasienRepository.delete(findKamarPasien.get());
        return String.format("Kamar pasien dengan id %s berhasil dihapus!", id);
    }

}
