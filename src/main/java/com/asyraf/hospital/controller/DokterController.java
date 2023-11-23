package com.asyraf.hospital.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.asyraf.hospital.dto.DokterRequest;
import com.asyraf.hospital.dto.DokterResponse;
import com.asyraf.hospital.service.DokterService;

@RestController
@RequestMapping("/dokter")
@CrossOrigin("*")
public class DokterController {

    private final DokterService dokterService;

    @Autowired
    public DokterController(DokterService dokterService) {
        this.dokterService = dokterService;
    }

    @GetMapping("/spesialisasi")
    public ResponseEntity<List<String>> getAllSpesialisasi() {
        return ResponseEntity.ok(dokterService.getAllSpesialisasi());
    }

    @GetMapping
    public ResponseEntity<List<DokterResponse>> getAll(@RequestParam String spesialisasi) {
        if (spesialisasi != null) {
            return ResponseEntity.ok(dokterService.getDokterBySpesialisasi(spesialisasi));
        }
        return ResponseEntity.ok(dokterService.getAllDokter());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DokterResponse> getById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(dokterService.getDokter(id));
    }

    @PostMapping
    public ResponseEntity<DokterResponse> addDokter(@RequestBody DokterRequest request) {
        return ResponseEntity.ok(dokterService.addDokter(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DokterResponse> updateDokter(@PathVariable("id") Integer id,
            @RequestBody DokterRequest request) {
        return ResponseEntity.ok(dokterService.updateDokter(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDokter(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(dokterService.deleteDokter(id));
    }

}
