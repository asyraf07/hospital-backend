package com.asyraf.hospital.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.asyraf.hospital.entity.Dokter;

@Repository
public interface DokterRepository extends JpaRepository<Dokter, Integer> {

    @Query("SELECT DISTINCT d.spesialisasi FROM Dokter d")
    List<String> findAllSpesialisasi();

    List<Dokter> findBySpesialisasiIgnoreCase(String spesialisasi);

}
