package com.asyraf.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.asyraf.hospital.entity.Pasien;

@Repository
public interface PasienRepository extends JpaRepository<Pasien, Integer> {
}
