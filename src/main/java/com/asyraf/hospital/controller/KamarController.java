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

import com.asyraf.hospital.dto.DetailKamar;
import com.asyraf.hospital.dto.KamarRequest;
import com.asyraf.hospital.dto.KamarResponse;
import com.asyraf.hospital.service.KamarService;

@RestController
@RequestMapping("/kamar")
public class KamarController {

    private final KamarService kamarService;

    @Autowired
    public KamarController(KamarService kamarService) {
        this.kamarService = kamarService;
    }

    @GetMapping("/detail/{noKamar}")
    public ResponseEntity<DetailKamar> getDetail(@PathVariable("noKamar") String noKamar) {
        return ResponseEntity.ok(kamarService.getDetailKamar(noKamar));
    }

    @GetMapping
    public ResponseEntity<List<KamarResponse>> getAll() {
        return ResponseEntity.ok(kamarService.getAllKamar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<KamarResponse> getById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(kamarService.getKamar(id));
    }

    @PostMapping
    public ResponseEntity<KamarResponse> addKamar(@RequestBody KamarRequest request) {
        return ResponseEntity.ok(kamarService.addKamar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<KamarResponse> updateKamar(@PathVariable("id") Integer id,
            @RequestBody KamarRequest request) {
        return ResponseEntity.ok(kamarService.updateKamar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteKamar(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(kamarService.deleteKamar(id));
    }

}
