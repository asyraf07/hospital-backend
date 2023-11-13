package com.asyraf.hospital.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asyraf.hospital.dto.KamarPasienRequest;
import com.asyraf.hospital.dto.KamarPasienResponse;
import com.asyraf.hospital.service.KamarPasienService;

@RestController
@RequestMapping("/kamarPasien")
public class KamarPasienController {

    @Autowired
    private KamarPasienService kamarPasienService;

    @GetMapping
    public ResponseEntity<List<KamarPasienResponse>> getAll() {
        return ResponseEntity.ok(kamarPasienService.getAllKamarPasien());
    }

    @GetMapping("/{id}")
    public ResponseEntity<KamarPasienResponse> getById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(kamarPasienService.getKamarPasien(id));
    }

    @PostMapping
    public ResponseEntity<KamarPasienResponse> addKamarPasien(@RequestBody KamarPasienRequest request) {
        return ResponseEntity.ok(kamarPasienService.addKamarPasien(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<KamarPasienResponse> updateKamarPasien(@PathVariable("id") Integer id,
            @RequestBody KamarPasienRequest request) {
        return ResponseEntity.ok(kamarPasienService.updateKamarPasien(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteKamarPasien(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(kamarPasienService.deleteKamarPasien(id));
    }

}
