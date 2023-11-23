package com.asyraf.hospital.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.asyraf.hospital.dto.InfoPasien;
import com.asyraf.hospital.entity.Pengobatan;

@Repository
public interface PengobatanRepository extends JpaRepository<Pengobatan, Integer> {

    @Query("SELECT new com.asyraf.hospital.dto.InfoPasien(p.user.nama, d.user.nama, pg.penyakit, kp.tanggalMasuk, kp.tanggalKeluar) " +
            "FROM Pengobatan pg " +
            "JOIN pg.pasien p " +
            "JOIN pg.dokter d " +
            "JOIN KamarPasien kp ON p.id=kp.pasien.id " +
            "WHERE kp.kamar.noKamar=:noKamar")
    List<InfoPasien> getInfoPasienByNoKamar(String noKamar);

    List<Pengobatan> findByPasienId(Integer id);
}
