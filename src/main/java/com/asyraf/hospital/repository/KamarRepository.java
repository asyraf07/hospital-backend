package com.asyraf.hospital.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.asyraf.hospital.entity.Kamar;

@Repository
public interface KamarRepository extends JpaRepository<Kamar, Integer> {
    Optional<Kamar> findByNoKamar(String noKamar);
}
