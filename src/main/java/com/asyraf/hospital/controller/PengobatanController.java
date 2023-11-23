package com.asyraf.hospital.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.asyraf.hospital.dto.PengobatanRequest;
import com.asyraf.hospital.dto.PengobatanResponse;
import com.asyraf.hospital.service.PengobatanService;

@RestController
@CrossOrigin("*")
@RequestMapping("/pengobatan")
public class PengobatanController {

    private final PengobatanService pengobatanService;

    @Autowired
    public PengobatanController(PengobatanService pengobatanService) {
        this.pengobatanService = pengobatanService;
    }

    @GetMapping
    public ResponseEntity<List<PengobatanResponse>> getAll(@RequestParam Integer pasienId) {
        if (pasienId != null) {
            return ResponseEntity.ok(pengobatanService.getAllPengobatanByPasienId(pasienId));
        }
        return ResponseEntity.ok(pengobatanService.getAllPengobatan());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PengobatanResponse> getById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(pengobatanService.getPengobatan(id));
    }

    @PostMapping
    public ResponseEntity<PengobatanResponse> addPengobatan(@RequestBody PengobatanRequest request) {
        return new ResponseEntity<>(pengobatanService.addPengobatan(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PengobatanResponse> updatePengobatan(@PathVariable("id") Integer id,
            @RequestBody PengobatanRequest request) {
        return ResponseEntity.ok(pengobatanService.updatePengobatan(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePengobatan(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(pengobatanService.deletePengobatan(id));
    }

}
