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

import com.asyraf.hospital.dto.PasienRequest;
import com.asyraf.hospital.dto.PasienResponse;
import com.asyraf.hospital.service.PasienService;

@RestController
@RequestMapping("/pasien")
public class PasienController {

    private final PasienService pasienService;

    @Autowired
    public PasienController(PasienService pasienService) {
        this.pasienService = pasienService;
    }

    @GetMapping
    public ResponseEntity<List<PasienResponse>> getAll() {
        return ResponseEntity.ok(pasienService.getAllPasien());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PasienResponse> getById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(pasienService.getPasien(id));
    }

    @PostMapping
    public ResponseEntity<PasienResponse> addPasien(@RequestBody PasienRequest request) {
        return ResponseEntity.ok(pasienService.addPasien(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PasienResponse> updatePasien(@PathVariable("id") Integer id,
            @RequestBody PasienRequest request) {
        return ResponseEntity.ok(pasienService.updatePasien(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePasien(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(pasienService.deletePasien(id));
    }

}
