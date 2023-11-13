package com.asyraf.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.asyraf.hospital.entity.Dokter;

@Repository
public interface DokterRepository extends JpaRepository<Dokter, Integer> {
}
