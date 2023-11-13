package com.asyraf.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.asyraf.hospital.entity.Pengobatan;

@Repository
public interface PengobatanRepository extends JpaRepository<Pengobatan, Integer> {
}
