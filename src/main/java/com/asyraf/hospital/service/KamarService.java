package com.asyraf.hospital.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asyraf.hospital.dto.KamarRequest;
import com.asyraf.hospital.dto.KamarResponse;
import com.asyraf.hospital.entity.Kamar;
import com.asyraf.hospital.entity.User;
import com.asyraf.hospital.repository.KamarRepository;
import com.asyraf.hospital.repository.UserRepository;

import lombok.SneakyThrows;

@Service
public class KamarService {

    @Autowired
    KamarRepository kamarRepository;

    public List<KamarResponse> getAllKamar() {
        List<KamarResponse> response = new ArrayList<>();
        List<Kamar> kamarList = kamarRepository.findAll();
        for (Kamar kamar : kamarList) {
            response.add(KamarResponse.builder()
                    .id(kamar.getId())
                    .noKamar(kamar.getNoKamar())
                    .build());
        }
        return response;
    }

    @SneakyThrows
    public KamarResponse getKamar(Integer id) {
        Optional<Kamar> find = kamarRepository.findById(id);
        if (!find.isPresent()) {
            throw new Exception();
        }
        Kamar kamar = find.get();
        return KamarResponse.builder()
                .id(kamar.getId())
                .noKamar(kamar.getNoKamar())
                .build();
    }

    @SneakyThrows
    public KamarResponse addKamar(KamarRequest request) {
        Kamar kamar = new Kamar();
        kamar.setNoKamar(request.getNoKamar());
        kamarRepository.save(kamar);
        return KamarResponse.builder()
                .id(kamar.getId())
                .noKamar(kamar.getNoKamar())
                .build();
    }

    @SneakyThrows
    public KamarResponse updateKamar(Integer id, KamarRequest request) {
        Optional<Kamar> findKamar = kamarRepository.findById(id);
        if (!findKamar.isPresent()) {
            throw new Exception();
        }
        Kamar kamar = findKamar.get();
        kamar.setNoKamar(request.getNoKamar());
        kamarRepository.save(kamar);
        return KamarResponse.builder()
                .id(kamar.getId())
                .noKamar(kamar.getNoKamar())
                .build();
    }

    public String deleteKamar(Integer id) {
        Optional<Kamar> findKamar = kamarRepository.findById(id);
        if (!findKamar.isPresent()) {
            return String.format("Tidak ada kamar dengan id %s", id);
        }
        kamarRepository.delete(findKamar.get());
        return String.format("Kamar dengan id %s berhasil dihapus!", id);
    }

}
