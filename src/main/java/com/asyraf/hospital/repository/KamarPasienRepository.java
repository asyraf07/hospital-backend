package com.asyraf.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.asyraf.hospital.entity.KamarPasien;

@Repository
public interface KamarPasienRepository extends JpaRepository<KamarPasien, Integer> {
}
